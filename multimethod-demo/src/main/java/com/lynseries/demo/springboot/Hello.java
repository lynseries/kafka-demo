package com.lynseries.demo.springboot;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lynseries@163.com
 * @version V1.0
 * @date 2020/3/10
 */
@Data
@Slf4j
@ToString
public class Hello {

    private String from;

    private String to;

    private String msg;


}
