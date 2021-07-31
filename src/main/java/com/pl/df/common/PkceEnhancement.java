package com.pl.df.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PkceEnhancement {

	public PkceEnhancement createCodeVerifierAndCodeChallange() {
		String createCodeVerifier = createCodeVerifier();
		String generateCodeChallange = generateCodeChallange(createCodeVerifier);
		
		return new PkceEnhancement(createCodeVerifier, generateCodeChallange);
	}
	
	public String createCodeVerifier() {
		byte[] codeVerifier = new byte[32];

		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(codeVerifier);

		String encodeToString = Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);

		return encodeToString;
	}

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
