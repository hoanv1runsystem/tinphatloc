package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MenuDto {
	private Long id;
	private String name;
	private String link;
	private int positon;
	private String active;
	private int deleteFlag;
}
