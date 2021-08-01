package com.pl.df.services.common;

import com.pl.df.dto.PkceDto;

public interface PkceEnhancementService {
	
	public PkceDto createCodeVerifierAndCodeChallange();
	public String createCodeVerifier();
	public String createCodeChallange(String codeVerifier);
	
}
