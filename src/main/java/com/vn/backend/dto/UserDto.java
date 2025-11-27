package com.vn.backend.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vn.auth.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private Long id;
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	private String passwordOld;

	private String image;
	private MultipartFile imageUpload;
	private List<Role> roles = new ArrayList<>();
}
