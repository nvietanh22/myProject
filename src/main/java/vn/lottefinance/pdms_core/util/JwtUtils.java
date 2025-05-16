package vn.lottefinance.pdms_core.util;

import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.oauth2.jose...
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
//    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
//
//    public static final String AUTHORITIES_KEY = "auth";
//
//    private JwtUtils() {
//    }
//
//    /**
//     * Get the login of the current user.
//     *
//     * @return the login of the current user.
//     */
//    public static Optional<String> getCurrentUserLogin() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
//    }
//
//    private static String extractPrincipal(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
//            return springSecurityUser.getUsername();
//        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
//            return jwt.getSubject();
//        } else if (authentication.getPrincipal() instanceof String s) {
//            return s;
//        }
//        return null;
//    }

    public static String extractApiKey(HttpServletRequest request) {
        String apiKey = request.getHeader("x-api-key");
        return apiKey;
    }

    public static String extractJwtToken(HttpServletRequest request) {
        // Extract the JWT token from the "Authorization" header
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Extract the token part after "Bearer "
        }

        return null;
    }

    public static String extractSignature(HttpServletRequest request) {
        // Extract the JWT token from the "Authorization" header
        String header = request.getHeader("token");
        return header;
    }
}
