package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.backend.dto.MenuDto;
import com.vn.backend.model.Menu;
import com.vn.backend.repository.MenuRepository;
import com.vn.backend.response.MenuResponse;
import com.vn.backend.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<MenuDto> getListAll(int deleteFlag) {
		List<Menu> categories = menuRepository.findByDeleteFlag(deleteFlag);
		List<MenuDto> results = new ArrayList<>();
		for (Menu cate : categories) {
			MenuDto dto = new MenuDto();
			BeanUtils.copyProperties(cate, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public MenuResponse getPagging(int deleteFlag, Pageable pagging) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuDto getDetail(Long id, int deleteFlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuDto add(MenuDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuResponse addCategory(MenuDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuDto update(MenuDto dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
