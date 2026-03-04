package EmpresaPkalab.config;

import org.n52.jackson.datatype.jts.JtsModule; // Asegúrate de que diga n52
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }
}