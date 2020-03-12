package com.lynseries.demo.kafkaapi;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author lynseries@163.com
 * @date 2020/2/29
 * @version V1.0
 */
public class MyKafkaProducer extends Thread{

    private KafkaProducer producer;

    private String topic;

    public MyKafkaProducer(final String topic){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG,"KAFKA-DEMO");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.101:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /**
         * 批量发送.
         */
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"10"); //每10个消息发送一次
       // properties.setProperty(ProducerConfig.LINGER_MS_CONFIG,"");//每多长时间发送一次
        producer = new KafkaProducer<Integer,String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {
        int i = 0;
        String msg = "MyKafkaProduce produce msg "+i;

        while (i<20){
            try {
                ProducerRecord<Integer,String> record = new ProducerRecord<>(topic,msg);
                //RecordMetadata metadata = (RecordMetadata) producer.send(record).get();

                producer.send(record, (metadata,e)->{
                    System.out.println("MyKafkaProduce send over:" + metadata.offset());
                });

                i++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyKafkaProducer producer = new MyKafkaProducer("kafkaapi-demo");
        producer.start();
    }
}
