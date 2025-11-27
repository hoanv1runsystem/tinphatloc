package com.vn.backend.service;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.AboutDto;
import com.vn.backend.response.AboutResponse;

public interface AboutService {
	AboutResponse getPaggingAbout(int deleteFlag, Pageable pagging);

	AboutDto getDetail(Long id, int deleteFlag);

	AboutDto add(AboutDto aboutDto);

	AboutDto update(AboutDto aboutDto) throws Exception;

	boolean delete(Long id) throws Exception;
}
