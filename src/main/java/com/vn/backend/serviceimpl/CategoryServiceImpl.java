package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vn.backend.dto.CategoryDto;
import com.vn.backend.model.Category;
import com.vn.backend.repository.CategoryRepository;
import com.vn.backend.response.CategoryResponse;
import com.vn.backend.service.CategoryService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getListAll(int deleteFlag) {
		List<Category> categories = categoryRepository.findByDeleteFlag(deleteFlag);
		List<CategoryDto> results = new ArrayList<>();
		for (Category cate : categories) {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(cate, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public CategoryResponse getPaggingCategory(int deleteFlag, Pageable pagging) {
		CategoryResponse results = new CategoryResponse();

		Page<Category> pages = categoryRepository.findAll(pagging);
		List<Category> categories = pages.getContent();
		List<CategoryDto> results1 = new ArrayList<>();
		for (Category cate : categories) {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(cate, dto);
			results1.add(dto);
		}
		results.setCategories(results1);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public CategoryDto getDetail(Long id, int deleteFlag) {
		Category category = categoryRepository.findByIdAndDeleteFlag(id, deleteFlag);
		CategoryDto dto = new CategoryDto();
		BeanUtils.copyProperties(category, dto);
		return dto;
	}

	@Override
	public CategoryDto add(CategoryDto categoryDto) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		Category result = categoryRepository.save(category);
		BeanUtils.copyProperties(result, categoryDto);
		return categoryDto;
	}

	@Override
	public CategoryResponse addCategory(CategoryDto categoryDto) {
		CategoryResponse categoryResponse = new CategoryResponse();
		List<Category> categories = categoryRepository.findByNameAndDeleteFlag(categoryDto.getName(),
				Constant.DELETE_FLAG_ACTIVE);
		if (categories != null && categories.size() > 0) {
			categoryResponse.setStatus(Constant.DUPLICATE);
			categoryResponse.setMessage(Message.NAME_CATEGORY_DUPLICATE);
			categoryResponse.setCategoryDto(categoryDto);
			return categoryResponse;
		}

		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		Category result = categoryRepository.save(category);
		BeanUtils.copyProperties(result, categoryDto);

		categoryResponse.setStatus(Constant.STATUS_SUCCSESS);
		categoryResponse.setMessage(Message.ADD_SUCCESS);
		categoryResponse.setCategoryDto(categoryDto);

		return categoryResponse;
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto) throws Exception {
		Optional<Category> option = categoryRepository.findById(categoryDto.getId());

		if (option.isPresent()) {
			Category category = option.get();
			if (!StringUtils.hasText(categoryDto.getName())) {
				category.setName(categoryDto.getName());
			}

			Category result = categoryRepository.save(category);
			BeanUtils.copyProperties(categoryDto, result);
			return categoryDto;
		} else {
			throw new Exception("Update category failure");
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<Category> option = categoryRepository.findById(id);

		if (option.isPresent()) {
			categoryRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
