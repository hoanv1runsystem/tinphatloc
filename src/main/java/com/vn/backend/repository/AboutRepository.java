package com.vn.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.About;

public interface AboutRepository extends JpaRepository<About, Long> {
	Page<About> findByDeleteFlag(int deleteFlag, Pageable pagging);

	About findByIdAndDeleteFlag(Long id, int deleteFlag);
}
