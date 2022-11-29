package miaplatform.core.pollinator;

import miaplatform.core.pollinator.model.HttpRequest;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PollinatorApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(PollinatorApplication.class);

	@Value("${message.topic.name}")
	private String topic;

	public static void main(String[] args) {
		ConfigurableApplicationContext context  = SpringApplication.run(PollinatorApplication.class, args);
		HttpRequestRepository repository = context.getBean(HttpRequestRepository.class);
		MessageProducer producer = context.getBean(MessageProducer.class);

		repository.deleteAll();
		repository.save(new HttpRequest(HttpMethod.GET,"https://jsonplaceholder.typicode.com/posts"));
		repository.save(new HttpRequest(HttpMethod.POST,"https://pokeapi.co/api/v2/pokemon/ditto"));
		repository.save(new HttpRequest(HttpMethod.POST,"https://jsonplaceholder.typicode.com/posts"));
		List<HttpRequest> requests = repository.findByMethod(HttpMethod.GET);

		for (HttpRequest request : requests) {
			LOGGER.info(request.toString());
		}

		producer.send("test-topic", "Hello World");

		context.close();
	}
}
