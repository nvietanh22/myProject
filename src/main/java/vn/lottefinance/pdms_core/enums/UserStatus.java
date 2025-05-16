package vn.lottefinance.pdms_core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("ACTIVE"),
    DEACTIVE("DEACTIVE");

    private final String value;
}
