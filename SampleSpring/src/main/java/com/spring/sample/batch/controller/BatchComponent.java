package com.spring.sample.batch.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchComponent {

	@Scheduled(cron = "0 0 0 * * *")
	public void cronTest1() {
		System.out.println("batch!!");
	}

}
