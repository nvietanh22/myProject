package vn.lottefinance.pdms_core.web.rest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.lottefinance.pdms_core.component.JwtResponse;
import vn.lottefinance.pdms_core.exception.BaseException;
import vn.lottefinance.pdms_core.service.core.AuthService;
import vn.lottefinance.pdms_core.service.core.dto.auth.AuthenticateRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.auth.RefreshTokenRequest;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    final static String LOG_MSG = "Login %s, username: %s, date: %s";
    final static String FAILED_MSG = "failed";
    final static String SUCCESS_MSG = "successfully";

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody AuthenticateRequestDto authenticateRequestDto) throws BaseException {
        ResponseEntity<JwtResponse> responseEntity = authService.login(authenticateRequestDto);
        String dateStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        if (responseEntity.getBody() == null || responseEntity.getBody().getAccessToken() == null) {
            log.info(String.format(LOG_MSG, FAILED_MSG, authenticateRequestDto.getUserName(), dateStr));
        } else {
            log.info(String.format(LOG_MSG, SUCCESS_MSG, authenticateRequestDto.getUserName(), dateStr));
        }
        return responseEntity;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseEntity<JwtResponse>> refreshToken(@RequestBody RefreshTokenRequest request) throws BaseException {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}
