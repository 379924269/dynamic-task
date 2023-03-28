package com.huazai.test.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务测试类
 */
@Slf4j
@Component("taskTest")
public class TaskTestMethod {
    public void test1(String params) {
        log.info("test1:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("test1 param：{}", params);
    }

    public void test2() {
        log.info("test2:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("test2");
    }

    public void test3(String params) {
        log.info("test3执行时间:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("test3执行有参示例任务,param：{}", params);
    }
}
