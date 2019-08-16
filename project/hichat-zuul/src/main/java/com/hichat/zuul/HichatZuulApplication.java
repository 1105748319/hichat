package com.hichat.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class HichatZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(HichatZuulApplication.class, args);
    }
}
