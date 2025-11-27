package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.ProductDto;
import com.vn.backend.response.ProductResponse;

public interface ProductService {

	List<ProductDto> getListProduct();

	ProductResponse getPaggingProduct(int deleteFlag, Pageable pagging);

	ProductDto getDetail(Long id, int deleteFlag);

	ProductDto add(ProductDto productDto);

	ProductResponse addValidate(ProductDto productDto);

	ProductResponse update(ProductDto productDto);

	boolean delete(Long id) throws Exception;

}
