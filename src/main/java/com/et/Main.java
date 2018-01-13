package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @EnableEurekaClient自动注册到注册中心
 * @RibbonClient引用ribbon 表示当前这个服务需要调用的其他的服务名称
 * @Bean在spring的容器中创建一个bean，用bean就一定用@Configuration
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Main {
	
	 public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
 
} 
