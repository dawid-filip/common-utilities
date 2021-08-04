package com.pl.df.services.common;

import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.pl.df.dto.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CodeConverterServiceImpl implements CodeConverterService {
	
	@Override
	public String basicAuthorization(User user) {
		String encodeBytes = Base64.getEncoder()
				.encodeToString((user.getUsername() + ":" + user.getPassword())
				.getBytes());
		
		String authorizationHeader = HttpHeaders.AUTHORIZATION + ": Basic " + encodeBytes;
		log.info("Converted " + user + " to Basic64 with output: " + authorizationHeader);
		
		return authorizationHeader;
	}
	
}
