package miaplatform.core.pollinator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "${message.topic.name}", groupId = "test-group-id")
    public void receive(String message) {
        LOGGER.info("received payload='{}'", message);
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    // other getters
    public CountDownLatch getLatch() {
        return latch;
    }
}

