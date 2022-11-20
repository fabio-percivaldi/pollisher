package miaplatform.core.pollinator;

import miaplatform.core.pollinator.model.HttpRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestService {
    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<String> response =  this.restTemplate.getForEntity(url, String.class);

        if(response.getStatusCode().isError()) {
            throw new HttpClientErrorException(response.getStatusCode());
        }
        return response.getBody();
    }

    public String pollingRequest(HttpRequest httpRequest) {
        String url = "https://jsonplaceholder.typicode.com/posts";
        if(httpRequest.method == HttpMethod.POST) {
            HttpEntity<Object> request = new HttpEntity<>(httpRequest.body);
        }

        return this.restTemplate.getForObject(url, String.class);
    }
}
