package vn.lottefinance.pdms_core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConsentSourceEnum {
    PDMS("PDMS"),
    LDP("LDP"),
    LOS("LOS"),
    RBP("RBP"),
    BNPL("BNPL"),
    DL("DL"),
    OL("OL");

    private String val;
}
