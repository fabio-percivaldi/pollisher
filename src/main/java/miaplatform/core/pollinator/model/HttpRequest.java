package miaplatform.core.pollinator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;

@Document(collection = "HttpRequest")
public class HttpRequest {
    @Id
    private String id;
    public String url;
    public HttpMethod method;
    public Object body;

    public HttpRequest(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format(
                "HttpRequest[id=%s, url='%s', method='%s']",
                id, url, method);
    }
}
