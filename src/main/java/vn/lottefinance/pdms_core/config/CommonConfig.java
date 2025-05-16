package vn.lottefinance.pdms_core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import vn.lottefinance.pdms_core.util.ProfileUtil;


@Configuration
public class CommonConfig {
    @Bean
    public ProfileUtil profileUtil(Environment environment) {
        return new ProfileUtil(environment);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
