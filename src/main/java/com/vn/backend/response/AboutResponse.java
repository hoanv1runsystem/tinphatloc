package com.vn.backend.response;

import java.util.ArrayList;
import java.util.List;

import com.vn.backend.dto.AboutDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AboutResponse {
	private int page;
	private int size;
	private int totalPages;
	private Long totalElement;
	private AboutDto about;
	private List<AboutDto> abouts = new ArrayList<>();
}
