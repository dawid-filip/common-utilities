package com.pl.df.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AesDto {
	
	private String secret;
	private String rowPassword;
	private String encryptedPassword;
	private String decrypteddPassword;
	private String key;
	
}
