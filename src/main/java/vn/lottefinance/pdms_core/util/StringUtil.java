package vn.lottefinance.pdms_core.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public static boolean eq(String val1, String val2) {
        if (StringUtils.isEmpty(val1)) {
            return StringUtils.isEmpty(val2);
        }
        return val1.equals(val2);
    }
}
