package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.VendorDto;
import com.vn.backend.response.VendorResponse;

public interface VendorService {
	List<VendorDto> getListAll(int deleteFlag);

	VendorResponse getPagging(int deleteFlag, Pageable pagging);

	VendorDto getDetail(Long id, int deleteFlag);

	VendorDto add(VendorDto dto);

	VendorResponse addValidate(VendorDto dto) throws Exception;

	VendorResponse update(VendorDto dto) throws Exception;

	boolean delete(Long id) throws Exception;
}
