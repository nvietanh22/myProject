package vn.lottefinance.pdms_core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CampainFileEnum {
    STT(0),
    CIF_NUMBER(1),
    IDCARD(2),
    CUST_NAME(3),
    PHONE_NO(4),
    EMAIL(5),
    NOTI_CHANNEL(6);

    private int position;
}
