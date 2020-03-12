package com.lynseries.demo.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/10
 */
@RestController
public class TestController {

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @GetMapping("/sayHi/{from}/{to}/{msg}")
    public HelloWorld sayHi(@PathVariable String from,@PathVariable String to,@PathVariable String msg){
        HelloWorld hi = new HelloWorld();
        hi.setFrom(from);
        hi.setMsg(msg);
        hi.setTo(to);
        kafkaTemplate.send("sayHi",hi);
        return hi;
    }

    @GetMapping("/sayHe/{from}/{to}/{msg}")
    public Hello sayHe(@PathVariable String from,@PathVariable String to,@PathVariable String msg){
        Hello hi = new Hello();
        hi.setFrom(from);
        hi.setMsg(msg);
        hi.setTo(to);
        kafkaTemplate.send("sayHe",hi);
        return hi;
    }
}
