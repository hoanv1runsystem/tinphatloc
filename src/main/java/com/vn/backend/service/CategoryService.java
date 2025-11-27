package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.CategoryDto;
import com.vn.backend.response.CategoryResponse;

public interface CategoryService {

	List<CategoryDto> getListAll(int deleteFlag);

	CategoryResponse getPaggingCategory(int deleteFlag, Pageable pagging);

	CategoryDto getDetail(Long id, int deleteFlag);

	CategoryDto add(CategoryDto categoryDto);
	
	CategoryResponse addCategory(CategoryDto categoryDto);

	CategoryDto update(CategoryDto categoryDto) throws Exception;

	boolean delete(Long id) throws Exception;

}
