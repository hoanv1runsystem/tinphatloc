package com.vn.backend.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SliceDto {
	private Long id;
	private String title;
	private String description;
	private String image;
	private int deleteFlag;
	private MultipartFile imageUpload;
	private Integer position;
	private List<SelectTagDto> selectPositions = new ArrayList<>();

	public SliceDto() {
		super();
		this.selectPositions.addAll(setDatePositions());
	}

	public SliceDto(Long id, String title, String description, String image, int deleteFlag, MultipartFile imageUpload,
			Integer position, List<SelectTagDto> selectPositions) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.deleteFlag = deleteFlag;
		this.imageUpload = imageUpload;
		this.position = position;
		this.selectPositions = selectPositions;
		this.selectPositions.addAll(setDatePositions());
	}

	public List<SelectTagDto> setDatePositions() {
		List<SelectTagDto> positions = new ArrayList<>();
		positions.add(new SelectTagDto("1", "Hiển thị bên trái trang chủ"));
		positions.add(new SelectTagDto("2", "Hiển thị bên phải => Trên"));
		positions.add(new SelectTagDto("3", "Hiển thị bên phải => Dưới"));
		return positions;

	}
}
