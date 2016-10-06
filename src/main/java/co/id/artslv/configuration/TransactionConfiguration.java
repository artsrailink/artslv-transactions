package co.id.artslv.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gemuruhgeo on 14/09/16.
 */
@Configuration
public class TransactionConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
