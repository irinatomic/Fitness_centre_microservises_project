package raf.fitness.reservation_servis.rest_templates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class ReservationClientCommunication {

    @Bean
    public RestTemplate reservationRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081/"));
        restTemplate.setInterceptors(Collections.singletonList(new TokenInterceptor()));
        return restTemplate;
    }

    private class TokenInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            HttpHeaders headers = httpRequest.getHeaders();
            headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZSI6IkFETUlOIn0.k95WjJgSrjQ2K5c556crlihPXtcVoaiDImZLFbov3MDrbA0Gf8rSLZwTIytVGpDwmyhJKdHG9S1Ubx8cHWfFsg");
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }
    }
}
