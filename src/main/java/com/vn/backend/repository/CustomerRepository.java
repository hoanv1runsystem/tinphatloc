package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByDeleteFlag(int deleteFlag, Pageable pagging);

	Customer findByIdAndDeleteFlag(Long id, int deleteFlag);
}
