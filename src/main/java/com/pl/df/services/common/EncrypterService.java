package com.pl.df.services.common;

import com.pl.df.dto.AesDto;

public interface EncrypterService {
	public String encryptToMD5(String rowValue);
	public AesDto encryptAndDecryptASE(String rowPassword);
}
