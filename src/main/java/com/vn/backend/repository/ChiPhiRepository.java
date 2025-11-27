package com.vn.backend.repository;

import com.vn.backend.model.ChiPhi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiPhiRepository extends JpaRepository<ChiPhi, Long> {
    List<ChiPhi> findByDeleteFlag(int deleteFlag, Pageable pagging);

    ChiPhi findByIdAndDeleteFlag(Long id, int deleteFlag);
}
