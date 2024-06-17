package com.confluent.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

    Logger logger = LoggerFactory.getLogger(Consumer.class.getName());

    @KafkaListener(topics = "quotes-woman-word-count", groupId = "spring-kafka-consumer")
    public void consume(ConsumerRecord<String, Long> consumerRecord) {
        logger.info("received value: " + consumerRecord.value() + ", key: " + consumerRecord.key());
    }
}
