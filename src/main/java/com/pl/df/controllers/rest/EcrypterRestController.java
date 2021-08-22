package com.pl.df.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pl.df.services.common.EncrypterService;

@RestController
@RequestMapping("/encrypter")
public class EcrypterRestController {

	@Autowired
	private EncrypterService encrypterService;
	
	@GetMapping("/md5")
	public String md5(@RequestParam("rowValue") String rowValue) {
		String encrypted = encrypterService.encryptToMD5(rowValue);
		return "Encrypted [" + rowValue + "] using MD5 to: \n" + encrypted;
	}
	
}
