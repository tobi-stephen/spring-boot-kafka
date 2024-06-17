package com.confluent.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StreamProcessor {
    private static final Logger logger = LoggerFactory.getLogger(StreamProcessor.class.getName());

    @Autowired
    public void process(StreamsBuilder builder) {

        final Serde<Integer> integerSerde = Serdes.Integer();
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        KStream<Integer, String> quoteLines = builder.stream("quotes-woman", Consumed.with(integerSerde, stringSerde));

        KTable<String, Long> wordCounts = quoteLines
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((k, v) -> v, Grouped.with(stringSerde, stringSerde))
                .count(Materialized.as("counts"));

        wordCounts.toStream().to("quotes-woman-word-count", Produced.with(stringSerde, longSerde));
    }
}
