package com.cos.test.domain.user.dto;

import com.cos.test.domain.user.dto.JoinReqDto;

import lombok.Data;

@Data
public class JoinReqDto {
	private String username;
	private String password;
	private String email;
}