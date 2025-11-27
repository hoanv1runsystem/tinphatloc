package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.AdvantageDto;
import com.vn.backend.response.AdvantageResponse;

public interface AdvantageService {
	List<AdvantageDto> getListAdvantage();

	List<AdvantageDto> getListAll(int deleteFlag);

	AdvantageResponse getPagging(int deleteFlag, Pageable pagging);

	AdvantageDto getDetail(Long id, int deleteFlag);

	AdvantageDto add(AdvantageDto dto);

	AdvantageResponse addValidate(AdvantageDto dto) throws Exception;

	AdvantageResponse update(AdvantageDto dto) throws Exception;

	boolean delete(Long id) throws Exception;
}
