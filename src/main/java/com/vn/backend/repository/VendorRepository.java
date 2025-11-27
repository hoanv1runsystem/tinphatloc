package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	List<Vendor> findByDeleteFlag(int deleteFlag);

	Vendor findByIdAndDeleteFlag(Long id, int deleteFlag);

	List<Vendor> findByNameAndDeleteFlag(String name, int deleteFlag);
}
