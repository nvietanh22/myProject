package vn.lottefinance.pdms_core.enums;

public enum SystemCodeEnum {
    SUCCESS("000", "Success"),
    ERROR("001", "Error"),
    BAD_REQUEST("400", "Bad Request"),
    FORBIDDEN("403", "Forbidden"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    SERVICE_UNAVAILABLE("503", "Service Unavailable"),
    GATEWAY_TIMEOUT("504", "Gateway Timeout"),
    CONFLICT("409", "Conflict"),
    SYSTEM_ERROR("00001", "System error. Please contact administrator!"),
    DUPLICATE("400", "Duplicate");

    private String code;
    private String message;

    SystemCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
