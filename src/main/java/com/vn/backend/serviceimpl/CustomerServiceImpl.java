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

import com.vn.backend.dto.CustomerDto;
import com.vn.backend.model.Customer;
import com.vn.backend.repository.CustomerRepository;
import com.vn.backend.response.CustomerResponse;
import com.vn.backend.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public CustomerResponse getPaggingCustomer(int deleteFlag, Pageable pagging) {
		CustomerResponse results = new CustomerResponse();

		Page<Customer> pages = customerRepository.findAll(pagging);
		List<Customer> categories = pages.getContent();
		List<CustomerDto> listResult = new ArrayList<>();
		for (Customer cate : categories) {
			CustomerDto dto = new CustomerDto();
			BeanUtils.copyProperties(cate, dto);
			listResult.add(dto);
		}
		results.setCustomers(listResult);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public CustomerDto getDetail(Long id, int deleteFlag) {
		Customer detail = customerRepository.findByIdAndDeleteFlag(id, deleteFlag);
		CustomerDto dto = new CustomerDto();
		BeanUtils.copyProperties(detail, dto);
		return dto;
	}

	@Override
	public CustomerDto add(CustomerDto customerDto) {
		Customer object = new Customer();
		BeanUtils.copyProperties(customerDto, object);
		object.setStatus(0);
		Customer result = customerRepository.save(object);
		BeanUtils.copyProperties(result, customerDto);
		return customerDto;
	}

	@Override
	public CustomerResponse addValidate(CustomerDto customerDto) {
//		CustomerResponse result = new CustomerResponse();
//		List<Category> categories = customerRepository.findByNameAndDeleteFlag(categoryDto.getName(),
//				Constant.DELETE_FLAG_ACTIVE);
//		if (categories != null && categories.size() > 0) {
//			result.setStatus(Constant.DUPLICATE);
//			result.setMessage(Message.NAME_CATEGORY_DUPLICATE);
//			result.setCategoryDto(categoryDto);
//			return result;
//		}
//
//		Category category = new Category();
//		BeanUtils.copyProperties(categoryDto, category);
//		Category result = categoryRepository.save(category);
//		BeanUtils.copyProperties(result, categoryDto);
//
//		categoryResponse.setStatus(Constant.STATUS_SUCCSESS);
//		categoryResponse.setMessage(Message.ADD_SUCCESS);
//		categoryResponse.setCategoryDto(categoryDto);
//
//		return categoryResponse;

		return null;
	}

	@Override
	public CustomerDto update(CustomerDto customerDto) throws Exception {
		Optional<Customer> option = customerRepository.findById(customerDto.getId());

		if (option.isPresent()) {
			Customer object = option.get();
			if (StringUtils.hasText(customerDto.getName())) {
				object.setName(customerDto.getName());
			}

			if (StringUtils.hasText(customerDto.getEmail())) {
				object.setEmail(customerDto.getEmail());
			}

			if (StringUtils.hasText(customerDto.getPhone())) {
				object.setPhone(customerDto.getPhone());
			}

			if (StringUtils.hasText(customerDto.getAddress())) {
				object.setAddress(customerDto.getAddress());
			}

			if (StringUtils.hasText(customerDto.getMessage())) {
				object.setMessage(customerDto.getMessage());
			}

			if (customerDto.getStatus() != null) {
				object.setStatus(customerDto.getStatus());
			}

			Customer result = customerRepository.save(object);
			BeanUtils.copyProperties(customerDto, result);
			return customerDto;
		} else {
			throw new Exception("Update category failure");
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<Customer> option = customerRepository.findById(id);

		if (option.isPresent()) {
			customerRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
