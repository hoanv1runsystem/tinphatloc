package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.SliceDto;
import com.vn.backend.response.SliceResponse;

public interface SliceService {
	List<SliceDto> getListAll(int deleteFlag);

	SliceResponse getPagging(int deleteFlag, Pageable pagging);

	SliceDto getDetail(Long id, int deleteFlag);

	SliceDto add(SliceDto dto);

	SliceResponse addValidate(SliceDto dto) throws Exception;

	SliceResponse update(SliceDto dto) throws Exception;

	boolean delete(Long id) throws Exception;
}
