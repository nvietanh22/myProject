package vn.lottefinance.pdms_core.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -4392067669965905019L;
    private final String accessToken;
    private final String refreshToken;
}
