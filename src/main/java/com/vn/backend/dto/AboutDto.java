package com.vn.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class AboutDto {
	private Long id;
	private String nameTab;
	private String title;
	private String header;
	private String decription;
	private Integer position;

	private List<SelectTagDto> selectPositions = new ArrayList<>();

	public AboutDto() {
		super();
		this.selectPositions.addAll(setDatePositions());
	}

	public AboutDto(Long id, String nameTab, String title, String header, String decription, Integer position,
			List<SelectTagDto> selectPositions) {
		super();
		this.id = id;
		this.nameTab = nameTab;
		this.title = title;
		this.header = header;
		this.decription = decription;
		this.position = position;
		this.selectPositions = selectPositions;
		this.selectPositions.addAll(setDatePositions());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameTab() {
		return nameTab;
	}

	public void setNameTab(String nameTab) {
		this.nameTab = nameTab;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<SelectTagDto> getSelectPositions() {
		return selectPositions;
	}

	public void setSelectPositions(List<SelectTagDto> selectPositions) {
		this.selectPositions = selectPositions;
	}

	public List<SelectTagDto> setDatePositions() {
		List<SelectTagDto> positions = new ArrayList<>();
		positions.add(new SelectTagDto("1", "Vị trí 1 tab"));
		positions.add(new SelectTagDto("2", "Vị trí 2 tab"));
		positions.add(new SelectTagDto("3", "Vị trí 3 tab"));
		return positions;

	}

}
