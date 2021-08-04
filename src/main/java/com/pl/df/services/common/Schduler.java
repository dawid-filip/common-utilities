package com.pl.df.services.common;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@EnableScheduling
@Log4j2
public class Schduler {

	@Scheduled(fixedDelay = 60000)
	public void start() {
		
	}
	
}
