package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.MenuDto;
import com.vn.backend.response.MenuResponse;

public interface MenuService {

	List<MenuDto> getListAll(int deleteFlag);

	MenuResponse getPagging(int deleteFlag, Pageable pagging);

	MenuDto getDetail(Long id, int deleteFlag);

	MenuDto add(MenuDto dto);

	MenuResponse addCategory(MenuDto dto);

	MenuDto update(MenuDto dto) throws Exception;

	boolean delete(Long id) throws Exception;

}
