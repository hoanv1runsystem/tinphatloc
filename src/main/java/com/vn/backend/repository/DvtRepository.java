package com.vn.backend.repository;

import com.vn.backend.model.Dvt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DvtRepository extends JpaRepository<Dvt, Long> {

    List<Dvt> findByDeleteFlagOrderByTenDvtAscMaDvtAsc(Integer deleteFlag);

}

