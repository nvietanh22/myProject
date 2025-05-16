package vn.lottefinance.pdms_core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class RestException extends ResponseStatusException {
    private String message;
    public RestException(HttpStatus status, String detail) {
        super(status, detail);
        this.message = detail;
    }

    @Override
    public String getReason() {
        return super.getReason();
    }
    public String getMessage() {
        return message;
    }
}
