package com.confluent.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AdminConfig {

    @Bean
    NewTopic quotesWoman() {
        return TopicBuilder.name("quotes-woman")
                        .partitions(10)
                        .replicas(3)
                        .build();
    }

    @Bean
    NewTopic quotesWomanWordCount() {
        return TopicBuilder.name("quotes-woman-word-count")
                .partitions(10)
                .replicas(3)
                .build();
    }
}
