package io.github.assets.web.rest;

import io.github.assets.service.FixedAssetsKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/fixed-assets-kafka")
public class FixedAssetsKafkaResource {

    private final Logger log = LoggerFactory.getLogger(FixedAssetsKafkaResource.class);

    private FixedAssetsKafkaProducer kafkaProducer;

    public FixedAssetsKafkaResource(FixedAssetsKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
