package vn.lottefinance.pdms_core.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {
    private String message;
    private String code;
    private HttpStatus status;

    public BaseException(String message, String code, HttpStatus status) {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
