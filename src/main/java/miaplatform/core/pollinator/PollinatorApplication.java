package miaplatform.core.pollinator;

import miaplatform.core.pollinator.model.HttpRequest;
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
			System.out.println(request);
		}

		producer.sendMessage("Hello World");

		context.close();
	}

	@Bean
	public MessageProducer messageProducer() {
		return new MessageProducer();
	}
	public static class MessageProducer {

		@Autowired
		private KafkaTemplate<String, String> kafkaTemplate;

		@Value(value = "${message.topic.name}")
		private String topicName;

		public void sendMessage(String message) {

			ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

			future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

				@Override
				public void onSuccess(SendResult<String, String> result) {
					System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
							.offset() + "]");
				}

				@Override
				public void onFailure(Throwable ex) {
					System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
				}
			});
		}
	}
}
