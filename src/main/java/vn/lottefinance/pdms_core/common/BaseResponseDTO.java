package vn.lottefinance.pdms_core.common;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BaseResponseDTO<T> {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    @Builder.Default
    private int status = 200;
    @Builder.Default
    private String code = "200";
    @Builder.Default
    private String message = "success";
    private T data;
    private Map<String, String> errors;
}
