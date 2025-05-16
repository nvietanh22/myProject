package vn.lottefinance.pdms_core.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public class ProfileUtil {
    @Getter
    static private PROFILE currentProfile;

    public ProfileUtil(Environment environment) {
        for (String profile : environment.getActiveProfiles()) {
            currentProfile = PROFILE.fromProfileName(profile);
        }

    }

    static private Boolean checkProfile(PROFILE profile) {
        return profile.equals(currentProfile);
    }

    static public Boolean isLocal() {
        return checkProfile(PROFILE.LOCAL);
    }

    static public Boolean isDev() {
        return checkProfile(PROFILE.DEV);
    }

    static public Boolean isUat() {
        return checkProfile(PROFILE.UAT);
    }

    static public Boolean isProd() {
        return checkProfile(PROFILE.PROD);
    }

    public enum PROFILE {
        LOCAL,
        DEV,
        UAT,
        PROD;

        static public PROFILE fromProfileName(String profileName) {
            PROFILE profile = PROFILE.LOCAL;

            for (PROFILE tempProfile : PROFILE.values()) {
                if (tempProfile.name().equals(profileName.toUpperCase())) {
                    profile = tempProfile;
                }
            }

            return profile;
        }
    }
}
