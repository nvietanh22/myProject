package vn.lottefinance.pdms_core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CampainNotiStatusEnum {
    PENDING("PENDING", "Record waiting to noti"),
    PROCESSING("PROCESSING", "Record in process to noti"),
    SUCCESS("SUCCESS", "Record noti success"),
    ERROR("ERROR", "Record noti error");

    private String code;
    private String desc;
}
