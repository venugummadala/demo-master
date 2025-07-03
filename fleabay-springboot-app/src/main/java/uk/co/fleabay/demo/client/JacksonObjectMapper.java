package uk.co.fleabay.demo.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonObjectMapper {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }


}
