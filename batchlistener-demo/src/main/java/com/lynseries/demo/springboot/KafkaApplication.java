package com.lynseries.demo.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/11
 */
@SpringBootApplication
@Slf4j
public class KafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class,args);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setBatchListener(true);
        factory.setMessageConverter(batchConverter());
        return factory;
    }

    @Bean
    public BatchMessagingMessageConverter batchConverter() {
        return new BatchMessagingMessageConverter(new StringJsonMessageConverter());
    }

    @KafkaListener(topics = "batchTopic")
    public void listens1(String[] msgs){
        System.out.println("listens1 receive :" +msgs);
        log.info(Arrays.toString(msgs));
    }
}
