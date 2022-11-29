package miaplatform.core.pollinator;

import miaplatform.core.pollinator.model.HttpRequest;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MongoDBRepositoryTest {

    @Test
    void assertInsertSucceeds(ConfigurableApplicationContext context) {
        HttpRequestRepository httpRequestRepository = context.getBean(HttpRequestRepository.class);

        httpRequestRepository.deleteAll();
        HttpRequest request = new  HttpRequest(HttpMethod.GET,"https://jsonplaceholder.typicode.com/posts");

        request = httpRequestRepository.save(request);

        List<HttpRequest> requests = httpRequestRepository.findAll();
        assertEquals(1, requests.size());
    }
}
