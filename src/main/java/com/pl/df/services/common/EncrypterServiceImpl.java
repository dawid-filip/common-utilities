package com.pl.df.services.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.pl.df.dto.AesDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EncrypterServiceImpl implements EncrypterService {

	@Override
	public AesDto encryptAndDecryptASE(String rowPassword) {
		AesDto aesDto = null;
		
		try {
			
			String keySecret = "secret";
			SecretKeySpec key = createSecretKey(keySecret);
			
			String encryptedPassword = encrypt(rowPassword, key);
			String decryptedPassword = decrypt(encryptedPassword, key);

			aesDto = new AesDto(keySecret, 
					rowPassword, encryptedPassword, decryptedPassword,
					base64Encode(key.getEncoded()));
			log.info("\n" + aesDto.toString());
			
			return aesDto;
		} catch (Exception e) {
			log.error("Could not encrypt [" + rowPassword + "] value, cause: " + e.getMessage());
		}
		
		log.warn("Could not encrypt [" + rowPassword + "] value.");
		return null;
	}

	private SecretKeySpec createSecretKey(String keySecret) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] secretPassword = keySecret.toCharArray();
		byte[] salt = new byte[16];
		int iterationCount = 10000;
		int keyLength = 256;
		
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		PBEKeySpec pbeKeySpec = new PBEKeySpec(secretPassword, salt, iterationCount, keyLength);
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
		return new SecretKeySpec(secretKey.getEncoded(), "AES");
	}
	private String encrypt(String password, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
		Cipher cipher = getClipher();
		cipher.init(Cipher.ENCRYPT_MODE, key);
		AlgorithmParameters algorithmParameters = cipher.getParameters();
		IvParameterSpec ivParameterSpec = algorithmParameters.getParameterSpec(IvParameterSpec.class);
		byte[] cryptoText = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
		byte[] iv = ivParameterSpec.getIV();
		return base64Encode(iv) + ":" + base64Encode(cryptoText);
	}
	private String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	private String decrypt(String password, SecretKeySpec key) throws GeneralSecurityException, IOException {
		String[] splitedPassword = password.split(":");
		String iv = splitedPassword[0];
		String value = splitedPassword[1];
		Cipher cipher = getClipher();
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
		byte[] decryptedBytes = cipher.doFinal(base64Decode(value));
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}
	private byte[] base64Decode(String value) throws IOException {
		return Base64.getDecoder().decode(value);
	}

	private Cipher getClipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
		return Cipher.getInstance("AES/CBC/PKCS5Padding");
	}
	
	
	
	

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
