package com.xidian.crypt;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: LvLiuWei
 * @date: 2017/12/11.
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.xidian.crypt"})
@MapperScan(basePackages = {"com.xidian.crypt.naivechain.mappers"})
public class NaivechainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NaivechainApplication.class, args);
    }
}
