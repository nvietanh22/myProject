package vn.lottefinance.pdms_core.web.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.lottefinance.pdms_core.component.JwtResponse;
import vn.lottefinance.pdms_core.component.JwtTokenProvider;
import vn.lottefinance.pdms_core.domain.User;
import vn.lottefinance.pdms_core.exception.BaseException;
import vn.lottefinance.pdms_core.exception.RestException;
import vn.lottefinance.pdms_core.repository.UserRepository;
import okhttp3.*;
import vn.lottefinance.pdms_core.service.core.UserService;
import vn.lottefinance.pdms_core.service.core.dto.auth.SsoAuthDTO;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sso")
@Slf4j
@RequiredArgsConstructor
public class SSOController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(180,TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .callTimeout(180, TimeUnit.SECONDS)
                .build();;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Value("${app.sso-default}")
    private String ssoPass;

    @Value("${sso.base-url}")
    private String ssoBaseUrl;

    @Value("${sso.realm}")
    private String ssoRealm;

    @Value("${sso.client-id}")
    private String ssoClientId;

    @Value("${sso.service.check-token}")
    private String ssoCheckToken;

    @Value("${sso.client-secrect}")
    private String ssoClientSecrect;

    @PostMapping("/get-local-token")
    public ResponseEntity<SsoAuthDTO.Response> getLocalToken(@RequestBody SsoAuthDTO.Request ssovm) throws Exception {
        log.debug("getLocalToken ......");
//        boolean checkToken = checkSSOToken(ssovm.getToken());
//        if (!checkToken) {
//            throw new RestException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
//        }

        boolean checkUser = checkTokenUser(ssovm.getUsername(), ssovm.getToken());
        if (!checkUser) {
            throw new RestException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
        }

        User userExit = userRepository.findFirstByUsername(ssovm.getUsername());
        if (userExit == null) {
            log.debug("User not found.. create new");
            User user = new User();
            user.setUsername(ssovm.getUsername());
            user.setFullname(null);
            user.setPhoneNumber(null);
            user.setIdentityNumber(RandomStringUtils.randomAlphanumeric(10));
            user.setEmail(ssovm.getEmail());
            user.setCreateBy("sso");
            user.setCreatedDate(LocalDateTime.now());
            user.setStatus("Active");
            String encryptedPassword = passwordEncoder.encode(ssoPass);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            final UserDetails userDetails = userService.loadUserByUsername(ssovm.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);
            return ResponseEntity.ok(new SsoAuthDTO.Response(token, null));
        } else {
            log.debug("User existed.... authen");
            authenticate(userExit.getUsername(), ssoPass, userExit.getPassword());
            final UserDetails userDetails = userService.loadUserByUsername(ssovm.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);

            return ResponseEntity.ok(new SsoAuthDTO.Response(token, null));

        }

    }

    private boolean checkSSOToken(String token) {
        String url = String.format("%s/realms/%s/protocol/openid-connect/token/introspect", ssoBaseUrl, ssoRealm);
        String credentials = Credentials.basic(ssoClientId, ssoClientSecrect);

        okhttp3.RequestBody body = new FormBody.Builder()
                .add("token", token)
                .add("client_id", ssoClientId)
                .add("client_secret", ssoClientSecrect)
                .build();
        log.debug("Request to SSO: {}", url);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", credentials)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(body)
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                log.debug("Response: {}", responseBody);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseBody);
                return root.path("active").asBoolean(false);
            } else {
                return false;
            }
        } catch (IOException e) {
            log.error("Check token sso error: {}", e.getMessage());
            return false;
        }
    }

    private boolean checkTokenUser(String user, String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String ssoUser = jwt.getClaim("preferred_username").asString();
            return user.equals(ssoUser);
        } catch (Exception ex) {
            log.error("Error extract sso token: {}", ex.getMessage());
            return false;
        }
    }

    private void authenticate(String username, String password, String rawPassword) throws Exception {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean isValid = passwordEncoder.matches(password, rawPassword);
            if (!isValid) {
                throw new BaseException("INVALID_CREDENTIALS", "403", HttpStatus.UNAUTHORIZED);
            }
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
