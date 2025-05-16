package vn.lottefinance.pdms_core.web.rest;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;

@RestController
@RequestMapping("/public")
@Slf4j
public class HomeController {
    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthcheck() throws Exception {
        String appVersion = System.getenv("APP_VERSION");
        if (StringUtils.isEmpty(appVersion)) {
            try {
                ClassPathResource resource = new ClassPathResource("/META-INF/MANIFEST.MF");
                Properties properties = new Properties();
                properties.load(resource.getInputStream());
                appVersion = properties.getProperty("Implementation-Version");
            } catch (IOException e) {
                log.error(e.getMessage());
                appVersion = "Unknown";
            }
        }

        if (appVersion == null) {
            appVersion = "1.0.0";
        }
        return ResponseEntity.ok("PDMS api is running with version: " + appVersion);
    }

}
