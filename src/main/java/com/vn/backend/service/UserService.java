package com.vn.backend.service;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.UserDto;
import com.vn.backend.response.UserResponse;

public interface UserService {

	UserResponse getPaggingUser(int deleteFlag, Pageable pagging);

	UserDto getDetail(Long id, int deleteFlag);

	UserDto add(UserDto userDto);

	UserResponse addVaildate(UserDto dto) throws Exception;

	UserDto update(UserDto userDto) throws Exception;

	boolean delete(Long id) throws Exception;
	
	UserResponse changePassword(UserDto dto);
	
	UserDto findByUsername(String username) throws Exception;

}
