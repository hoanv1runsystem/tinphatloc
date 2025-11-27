package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Slice;

public interface SliceRepository extends JpaRepository<Slice, Long> {
	List<Slice> findByPosition(Integer type);

	List<Slice> findByDeleteFlag(int deleteFlag);

	Slice findByIdAndDeleteFlag(Long id, int deleteFlag);

	List<Slice> findByTitleAndDeleteFlag(String name, int deleteFlag);

}
