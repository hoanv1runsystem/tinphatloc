package com.vn.backend.service;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.CustomerDto;
import com.vn.backend.response.CustomerResponse;

public interface CustomerService {
	CustomerResponse getPaggingCustomer(int deleteFlag, Pageable pagging);

	CustomerDto getDetail(Long id, int deleteFlag);

	CustomerDto add(CustomerDto customerDto);

	CustomerResponse addValidate(CustomerDto customerDto);

	CustomerDto update(CustomerDto customerDto) throws Exception;

	boolean delete(Long id) throws Exception;
}
