package com.lynseries.demo.springboot;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/10
 */
@Component
@KafkaListener(groupId = "multiGroup",topics = {"sayHi","sayHe"})
public class MultiMethodHandle {


    @KafkaHandler
    public void handle1(HelloWorld helloWorld){
        System.out.println("handle1 receive " + helloWorld.toString());
    }

    @KafkaHandler
    public void handle2(Hello helloWorld){
        System.out.println("handle2 receive " + helloWorld.toString());
    }
}
