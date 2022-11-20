package miaplatform.core.pollinator;

import miaplatform.core.pollinator.model.HttpRequest;
import org.bson.Document;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoDBRepositoryTest {
    private static final String HOST = "localhost";
    private static final String PORT = "27017";
    private static final String DB = "pollinator";
    private static final String USER = "admin";
    private static final String PASS = "password";

    private void assertInsertSucceeds(ConfigurableApplicationContext context) {
        String name = "A";

        HttpRequestRepository httpRequestRepository = context.getBean(HttpRequestRepository.class);

        httpRequestRepository.deleteAll();
        HttpRequest request = new  HttpRequest(HttpMethod.GET,"https://jsonplaceholder.typicode.com/posts");

        request = httpRequestRepository.save(request);

        assertNotNull(httpRequestRepository.findAll());
    }
}
