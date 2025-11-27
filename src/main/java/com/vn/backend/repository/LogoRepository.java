package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Logo;

public interface LogoRepository extends JpaRepository<Logo, Long> {
	List<Logo> findByPosition(Integer type);

	List<Logo> findByDeleteFlag(int deleteFlag);

	Logo findByIdAndDeleteFlag(Long id, int deleteFlag);

}
