package vn.lottefinance.pdms_core.service.core;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import vn.lottefinance.pdms_core.component.JwtResponse;
import vn.lottefinance.pdms_core.exception.BaseException;
import vn.lottefinance.pdms_core.service.core.dto.auth.AuthenticateRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.auth.RefreshTokenRequest;

public interface AuthService {
    ResponseEntity<JwtResponse> login(AuthenticateRequestDto request) throws BaseException;

    ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws BaseException;
}
