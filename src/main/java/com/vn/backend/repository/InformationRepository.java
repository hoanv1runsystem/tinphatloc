package com.vn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Customer;

public interface InformationRepository extends JpaRepository<Customer, Long> {

}
