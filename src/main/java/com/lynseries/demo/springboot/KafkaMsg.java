package com.lynseries.demo.springboot;

import lombok.Data;
import lombok.ToString;

/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/3
 */
@Data
@ToString
public class KafkaMsg {

    private int num;

    private String msg;

    private String creater;

    private String topic;



}
