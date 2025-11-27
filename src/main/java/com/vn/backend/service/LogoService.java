package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.LogoDto;
import com.vn.backend.response.LogoResponse;

public interface LogoService {
	List<LogoDto> getListAll(int deleteFlag);

	LogoResponse getPagging(int deleteFlag, Pageable pagging);

	LogoDto getDetail(Long id, int deleteFlag);

	LogoDto add(LogoDto dto);

	LogoResponse addValidate(LogoDto dto) throws Exception;

	LogoResponse update(LogoDto dto) throws Exception;

	boolean delete(Long id) throws Exception;
}
