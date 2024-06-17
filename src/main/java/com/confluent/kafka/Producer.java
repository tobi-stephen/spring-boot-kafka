package com.confluent.kafka;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Component
class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class.getName());

    private final KafkaTemplate<Integer, String> template;
    Faker faker;

//    @EventListener(ApplicationStartedEvent.class)
    public void generate() {
        faker = Faker.instance();

        Flux<Long> interval = Flux.interval(Duration.ofMillis(100));
        Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

        Flux.zip(interval, quotes)
                .map(it -> template.send("quotes-man", faker.random().nextInt(42), it.getT2()))
                .blockLast();
    }

    @EventListener(ApplicationStartedEvent.class)
    public void generateW() {
        faker = Faker.instance();

        Flux<Long> interval = Flux.interval(Duration.ofMillis(100));
        Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.yoda().quote()));

        Flux.zip(interval, quotes)
                .map(it -> template.send("quotes-woman", faker.random().nextInt(42), it.getT2()))
                .blockLast();
    }
}