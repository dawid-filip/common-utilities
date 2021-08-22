package com.pl.df.services.common;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EncrypterServiceImpl implements EncrypterService {

	@Override
	public String encryptToMD5(String rowValue) {
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			log.error("Wrong algorithm: " + e.getMessage());
		}

		if (rowValue == null || rowValue.isEmpty()) {
			log.warn("Could not encrypt empty value.");
			return null;
		}
		
		try {
			byte[] raw = null;
			byte[] stringBytes = null;
			stringBytes = rowValue.getBytes("UTF8");
			
			synchronized (messageDigest) {
				raw = messageDigest.digest(stringBytes);
			}
			
			Encoder encoder = Base64.getEncoder();
			String encoded = new String(encoder.encode(raw));
			
			log.info("Encrypted " + rowValue + " to MD5: " + encoded);
			return encoded;
		} catch (Exception se) {
			log.error("Could not encrypt [" + rowValue + "] value.");
		}
		
		log.warn("Could not encrypt [" + rowValue + "] value.");
		return null;
	}

}
