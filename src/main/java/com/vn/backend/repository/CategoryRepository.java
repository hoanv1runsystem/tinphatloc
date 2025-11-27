package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByDeleteFlag(int deleteFlag);

	Category findByIdAndDeleteFlag(Long id, int deleteFlag);
	
	List<Category> findByNameAndDeleteFlag(String name, int deleteFlag);

}
