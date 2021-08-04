package com.pl.df.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pl.df.dto.User;
import com.pl.df.services.common.CodeConverterService;

@RestController
@RequestMapping("/code-coverter")
public class CodeConverterRestController {
	
	@Autowired
	private CodeConverterService codeConverterService;
	
	// http://localhost:8001/code-coverter/basic64
	@PostMapping("/basic64")
	public String getPkceCodes(@RequestBody User user) {
		return codeConverterService.basicAuthorization(user);
	}
	
	// http://localhost:8001/code-coverter/basic64?username=myUserName&password=myPassword
	@GetMapping("/basic64")	// TODO: not safe, the POST request should be used instead
	public String getPkceCodes(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = new User(username, password);
		return codeConverterService.basicAuthorization(user);
	}
	
	
}
