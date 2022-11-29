package miaplatform.core.pollinator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.timeout;

@SpringBootTest
@DirtiesContext
public class KafkaProducerTest {

    @Value("${message.topic.name}")
    private String topic;

    @Autowired
    private KafkaConsumer consumer;

    @Test
    public void test(ConfigurableApplicationContext context) throws InterruptedException {
        MessageProducer producer = context.getBean(MessageProducer.class);
        producer.send(this.topic, "Hello World");

        consumer.resetLatch();
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);
    }

}
