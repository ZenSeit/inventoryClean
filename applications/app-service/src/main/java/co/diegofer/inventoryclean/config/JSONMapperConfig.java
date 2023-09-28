package co.diegofer.inventoryclean.config;

import co.diegofer.inventoryclean.serializer.JSONMapper;
import co.diegofer.inventoryclean.serializer.JSONMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSONMapperConfig {
    @Bean
    public JSONMapper jsonMapper() {
        return new JSONMapperImpl();
    }
}
