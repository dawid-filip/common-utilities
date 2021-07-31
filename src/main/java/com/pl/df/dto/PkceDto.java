package com.pl.df.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PkceDto {

	private String codeVerifier;
	private String codeChallange;
	
}
