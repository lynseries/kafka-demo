package com.lynseries.demo.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/3
 */
@RestController
public class MsgController {

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @GetMapping("/produce/{msg}")
    public KafkaMsg produceMsg(@PathVariable String msg,HttpServletRequest request){
        KafkaMsg kafkaMsg = new KafkaMsg();
        kafkaMsg.setCreater(request.getRemoteHost());
        kafkaMsg.setMsg(msg);
        kafkaMsg.setNum(request.getRemotePort());
        kafkaMsg.setTopic("springboot_kafka");

        kafkaTemplate.send(kafkaMsg.getTopic(),kafkaMsg);
        return kafkaMsg;
    }


}
