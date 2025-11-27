package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InformationDto {

	private Long id;
	private String name;
	private String email;
	private String phone;
	private String googelMap;
	private String address;
}
