package com.pl.df.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.df.dto.PkceDto;
import com.pl.df.services.common.PkceEnhancementService;

@RestController
@RequestMapping("/pkce")
public class PkceRestController {

	@Autowired
	private PkceEnhancementService pkceEnhancementService;

	// http://localhost:8001/pkce/codes
	@GetMapping("codes")
	public PkceDto getPkceCodes() {
		return pkceEnhancementService.createCodeVerifierAndCodeChallange();
	}
	
	// http://localhost:8001/pkce/code-verifier
	@GetMapping("code-verifier")
	public String getPkceCodeVerifier() {
		return pkceEnhancementService.createCodeVerifier();
	}
	
	// http://localhost:8001/pkce/code-challenge
	@GetMapping("code-challenge")
	public String getPkceCodeChallange() {
		return pkceEnhancementService.createCodeChallange(pkceEnhancementService.createCodeVerifier());
	}
	
}

