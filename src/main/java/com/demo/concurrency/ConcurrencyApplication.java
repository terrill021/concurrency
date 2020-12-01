package com.demo.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class ConcurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrencyApplication.class, args);
	}

}
