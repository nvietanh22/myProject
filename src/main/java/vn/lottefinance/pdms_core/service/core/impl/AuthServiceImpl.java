package vn.lottefinance.pdms_core.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import vn.lottefinance.pdms_core.component.JwtResponse;
import vn.lottefinance.pdms_core.component.JwtTokenProvider;
import vn.lottefinance.pdms_core.domain.User;
import vn.lottefinance.pdms_core.enums.SystemCodeEnum;
import vn.lottefinance.pdms_core.exception.BaseException;
import vn.lottefinance.pdms_core.repository.UserRepository;
import vn.lottefinance.pdms_core.service.core.AuthService;
import vn.lottefinance.pdms_core.service.core.UserService;
import vn.lottefinance.pdms_core.service.core.dto.auth.AuthenticateRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.auth.RefreshTokenRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<JwtResponse> login(AuthenticateRequestDto request) throws BaseException {
        try {
            User user = userRepository.findFirstByUsername(request.getUserName());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse("Tài khoản không tồn tại", ""));
            }

            authenticate(request.getUserName(), request.getPassword(), user.getPassword());

            final UserDetails userDetails = userService.loadUserByUsername(request.getUserName());
            String accessToken = jwtTokenProvider.generateToken(userDetails);
            String refreshToken = jwtTokenProvider.generateRefreshToken(request.getUserName());
            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));

        } catch (Exception e) {
            if ("INVALID_CREDENTIALS".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new JwtResponse("Sai tên đăng nhập hoặc mật khẩu", ""));
            }
            throw new BaseException(e.getMessage(), SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    public static String getCurrentUser() {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    @Override
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws BaseException {
        String refreshToken = request.getRefreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new JwtResponse("Refresh token không hợp lệ hoặc đã hết hạn", ""));
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userService.loadUserByUsername(username);
        String newAccessToken = jwtTokenProvider.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken)); // reuse old refreshToken
    }
}