package bricks.trading.adapters.outbound.externalservice.business.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@RequiredArgsConstructor
public class BusinessClientConfig {

    @Bean
    RestClient businessRestClient() throws URISyntaxException {
        return RestClient.builder().baseUrl("https://api.develop.bricks.com.ar").build();

    }
}
