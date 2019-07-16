package io.github.assets.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FixedAssetsKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(FixedAssetsKafkaConsumer.class);
    private static final String TOPIC = "topic_fixedassets";

    @KafkaListener(topics = "topic_fixedassets", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
