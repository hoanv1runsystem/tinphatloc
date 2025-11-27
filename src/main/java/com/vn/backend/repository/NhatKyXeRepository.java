package com.vn.backend.repository;

import com.vn.backend.model.NhatKyXe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NhatKyXeRepository extends JpaRepository<NhatKyXe, Long> {
    List<NhatKyXe> findByDeleteFlag(int deleteFlag, Pageable pagging);

    NhatKyXe findByIdAndDeleteFlag(Long id, int deleteFlag);
}
