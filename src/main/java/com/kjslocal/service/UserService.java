package com.kjslocal.service;

import java.util.List;

import com.kjslocal.dto.UserRequestDto;
import com.kjslocal.dto.UserResponseDto;
import com.kjslocal.exceptions.UserException;

public interface UserService {
	
	UserResponseDto addUser(UserRequestDto req) throws UserException;
	
	List<UserResponseDto> getUsers() throws UserException;
	
	UserResponseDto getUserById(int id) throws UserException;
	
	UserResponseDto updateUser(int id, UserRequestDto req) throws UserException;
	
	String deleteUser(int id) throws UserException;
	
	void deleteMultipleUsers(List<Integer> ids) throws UserException;

}
