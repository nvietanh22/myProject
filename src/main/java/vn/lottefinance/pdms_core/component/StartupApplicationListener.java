package vn.lottefinance.pdms_core.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import vn.lottefinance.pdms_core.properties.EsbProperties;

@Slf4j
@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private EsbProperties esbProperties;
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        log.info("=================Application config==================");
        log.info(mapper.writeValueAsString(esbProperties));
        log.info("=================------------------==================");
    }
}
