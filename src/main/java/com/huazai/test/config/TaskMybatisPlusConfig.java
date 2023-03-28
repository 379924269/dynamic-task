package com.huazai.test.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = {"com.huazai.test.mapper*"})
public class TaskMybatisPlusConfig {
}
