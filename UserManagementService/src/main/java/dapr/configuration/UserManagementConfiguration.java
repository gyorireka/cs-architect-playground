package dapr.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

@Configuration
public class UserManagementConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public DaprClient daprClient() {
        return new DaprClientBuilder().build();
    }
}
