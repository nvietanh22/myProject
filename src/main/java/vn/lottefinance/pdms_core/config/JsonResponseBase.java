package vn.lottefinance.pdms_core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonResponseBase<T> {
    private T data;
    private String status;
    private String message;
}
