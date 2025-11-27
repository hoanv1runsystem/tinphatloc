package com.vn.backend.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String description;
	private Integer position;
	private String image;
	private int deleteFlag;
	private MultipartFile imageUpload;
	private List<SelectTagDto> selectPositions = new ArrayList<>();

	public LogoDto() {
		super();
		this.selectPositions.addAll(setDatePositions());
	}

	public LogoDto(Long id, String firstName, String lastName, String description, Integer position, String image,
			int deleteFlag, MultipartFile imageUpload, List<SelectTagDto> selectPositions) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.position = position;
		this.image = image;
		this.deleteFlag = deleteFlag;
		this.imageUpload = imageUpload;
		this.selectPositions = selectPositions;
		this.selectPositions.addAll(setDatePositions());
	}

	public List<SelectTagDto> setDatePositions() {
		List<SelectTagDto> positions = new ArrayList<>();
		positions.add(new SelectTagDto("1", "Header trang chủ"));
		positions.add(new SelectTagDto("2", "Footer trang chủ"));
		return positions;

	}
}
