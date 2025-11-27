package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByDeleteFlag(int deleteFlag, Pageable pagging);

	Product findByIdAndDeleteFlag(Long id, int deleteFlag);

	List<Product> findByProductCodeAndDeleteFlag(String productCode, int deleteFlag);

	List<Product> findByNameAndDeleteFlag(String name, int deleteFlag);
}
