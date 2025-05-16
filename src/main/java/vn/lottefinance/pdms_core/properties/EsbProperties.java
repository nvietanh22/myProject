package vn.lottefinance.pdms_core.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ToString
@ConfigurationProperties(prefix = "external.esb")
public class EsbProperties {
    private String baseUrl;
    private Credential credential;
    private Services services;

    public static class Credential {
        private String user;
        private String pass;

        // Getters and Setters
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
        public String getPass() { return pass; }
        public void setPass(String pass) { this.pass = pass; }
    }

    public static class Services {
        private Notification notification;

        // Getters and Setters
        public Notification getNotification() { return notification; }
        public void setNotification(Notification notification) { this.notification = notification; }

        public static class Notification {
            private String email;
            private String zns;
            private String sms;

            // Getters and Setters
            public String getEmail() { return email; }
            public void setEmail(String email) { this.email = email; }

            public String getZns() { return zns; }
            public void setZns(String zns) { this.zns = zns; }

            public String getSms() { return sms; }
            public void setSms(String sms) { this.sms = sms; }
        }
    }

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public Credential getCredential() { return credential; }
    public void setCredential(Credential credential) { this.credential = credential; }

    public Services getServices() { return services; }
    public void setServices(Services services) { this.services = services; }
}
