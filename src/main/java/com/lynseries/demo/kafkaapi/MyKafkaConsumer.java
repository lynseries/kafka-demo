package com.lynseries.demo.kafkaapi;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author lynseries@163.com
 * @date 2020/2/29
 * @version V1.0
 */
public class MyKafkaConsumer extends Thread{

    private String topic;

    private KafkaConsumer<Integer,String> consumer;

    public MyKafkaConsumer(final String topic) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.101:9092");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"consumer_3");
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG,"MyKafkaConsumer");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");//
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");//自动提交（批量确认）
        this.consumer = new KafkaConsumer<Integer, String>(properties);
        this.topic = topic;
    }


    @Override
    public void run() {
        consumer.subscribe(Collections.singleton(topic));
        long seconds = 1L;
        while (true){
            ConsumerRecords<Integer,String> records= consumer.poll(seconds);
            records.forEach(record ->{
                System.out.println(record.key() + ":" + record.value() +"->" + record.offset());
            });
        }
    }

    public static void main(String[] args) {
        MyKafkaConsumer consumer = new MyKafkaConsumer("kafkaapi-demo");
        consumer.start();
    }
}
