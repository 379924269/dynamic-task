//package com.huazai.test;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.env.Environment;
//
///**
// * 动态任务
// */
//@Slf4j
//@SpringBootApplication
//public class DynamicTaskApplication {
//
//    public static void main(String[] args) {
//        final ConfigurableApplicationContext application = SpringApplication.run(DynamicTaskApplication.class, args);
//        Environment env = application.getEnvironment();
//        String port = env.getProperty("server.port");
//        // 注意：applicationName 前后都有斜杠
//        String applicationName = env.getProperty("server.servlet.context-path");
//        if (applicationName == null) {
//            applicationName = "/";
//        }
//        log.info("Swagger-UI: http://localhost:{}{}doc.html", port == null ? "8080" : port, applicationName);
//    }
//
//}
