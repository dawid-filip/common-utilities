package com.pl.df.services.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.pl.df.dto.PkceDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PkceEnhancementServiceImpl implements PkceEnhancementService {

	@Override
	public PkceDto createCodeVerifierAndCodeChallange() {
		String createCodeVerifier = createCodeVerifier();
		String generateCodeChallange = generateCodeChallange(createCodeVerifier);
		
		PkceDto pkceDto = new PkceDto(createCodeVerifier, generateCodeChallange);
		log.info(pkceDto.toString());
		
		return pkceDto;
	}
	
	@Override
	public String createCodeVerifier() {
		byte[] codeVerifier = new byte[32];

		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(codeVerifier);

		String encodeToString = Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);

		return encodeToString;
	}

	@Override
	public String generateCodeChallange(String codeVerifier) {
		try {
			byte[] bytes = codeVerifier.getBytes("Us-ASCII");
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(bytes, 0, bytes.length);

			byte[] digest = messageDigest.digest();

			String encodeToString = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);

			return encodeToString;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			log.info("Exception during code challange creation", e);
			return null;
		}
	}

}
