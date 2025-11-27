package com.vn.backend.repository;

import com.vn.backend.model.CapNhienLieu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapNhienLieuRepository extends JpaRepository<CapNhienLieu, Long> {
    List<CapNhienLieu> findByDeleteFlag(Integer deleteFlag, Pageable pagging);

    CapNhienLieu findByIdAndDeleteFlag(Long id, Integer deleteFlag);
}
