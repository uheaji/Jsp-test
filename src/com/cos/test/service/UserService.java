package com.cos.test.service;

import com.cos.test.domain.user.User;
import com.cos.test.domain.user.dto.LoginReqDto;
import com.cos.test.domain.user.UserDao;
import com.cos.test.domain.user.dto.JoinReqDto;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}

	public int 회원가입(JoinReqDto dto) {
		int result = userDao.save(dto);
		return result;
	}
	
	public int 유저네임중복체크(String username) {
		int result = userDao.findByUsername(username);
		return result;
	}

	public User 로그인(LoginReqDto dto) {
		return userDao.findByUsernameAndPassword(dto);
	}

}
