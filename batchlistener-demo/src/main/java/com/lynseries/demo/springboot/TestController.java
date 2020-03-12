package com.lynseries.demo.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/11
 */
@RestController
public class TestController {

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @GetMapping("/sayHello/{msg}")
    public String sayHello(@PathVariable String msg){
        final String[] msgs =  StringUtils.commaDelimitedListToStringArray(msg);

        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback<Object, Object, Object>() {
            @Override
            public Object doInOperations(KafkaOperations<Object, Object> kafkaTemplate) {
                for(String msg:msgs){
                    TestController.this.kafkaTemplate.send("batchTopic",msg);
                }

                return null;
            }
        });
        return msg;
    }
}
