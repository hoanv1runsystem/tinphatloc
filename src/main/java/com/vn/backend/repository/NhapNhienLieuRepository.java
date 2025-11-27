package com.vn.backend.repository;

import com.vn.backend.model.NhapNhienLieu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NhapNhienLieuRepository extends JpaRepository<NhapNhienLieu, Long> {
    List<NhapNhienLieu> findByDeleteFlag(Integer deleteFlag, Pageable pagging);

    NhapNhienLieu findByIdAndDeleteFlag(Long id, Integer deleteFlag);
}
