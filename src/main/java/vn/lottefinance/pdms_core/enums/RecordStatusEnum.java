package vn.lottefinance.pdms_core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecordStatusEnum {
    ACTIVE(1),
    INACTIVE(0);
    private int val;

}
