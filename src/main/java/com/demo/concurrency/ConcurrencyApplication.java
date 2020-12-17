package com.demo.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class ConcurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrencyApplication.class, args);
	}

	@Bean
	public Executor threadPollconfig(){
		return Executors.newFixedThreadPool(10);
	}

}
