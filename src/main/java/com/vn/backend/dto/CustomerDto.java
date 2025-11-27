package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerDto {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String message;
	private Integer status;
	private int deleteFlag;
}
