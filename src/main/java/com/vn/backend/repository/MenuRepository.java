package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findByDeleteFlag(int deleteFlag);

}
