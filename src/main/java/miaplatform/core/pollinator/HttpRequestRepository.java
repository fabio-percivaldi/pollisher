package miaplatform.core.pollinator;

import miaplatform.core.pollinator.model.HttpRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;

import java.util.Date;
import java.util.List;

public interface HttpRequestRepository extends MongoRepository<HttpRequest, String> {
    public List<HttpRequest> findByMethod(HttpMethod method);
}
