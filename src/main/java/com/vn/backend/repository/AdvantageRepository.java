package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Advantage;

public interface AdvantageRepository extends JpaRepository<Advantage, Long> {
	List<Advantage> findByNameAndDeleteFlag(String name, int deleteFlag);

	List<Advantage> findByDeleteFlag(int deleteFlag);

	Advantage findByIdAndDeleteFlag(Long id, int deleteFlag);
}
