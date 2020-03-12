package com.lynseries.demo.springboot;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;
//import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;


/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/3
 */
@Slf4j
@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class,args);

        for(String beanName:context.getBeanDefinitionNames()){
            System.out.println(beanName);
        }
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory,
            KafkaTemplate<Object, Object> template) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(0L, 2))); // dead-letter after 3 tries
        return factory;
    }

    @KafkaListener(groupId = "consumer1",topics = "springboot_kafka")
    public void listen(KafkaMsg kafkaMsg){
        log.info("Received from " + kafkaMsg.toString());
    }

    @KafkaListener(id = "dltGroup", topics = "springboot_kafka.DLT")
    public void dltListen(String in) {
        log.info("Received from DLT: " + in);
    }

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

   /* @Bean
    public NewTopic topic() {
        return new NewTopic("springboot_kafka", 1, (short) 1);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic("springboot_kafka.DLT", 1, (short) 1);
    }*/
}
