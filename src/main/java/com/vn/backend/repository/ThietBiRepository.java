package com.vn.backend.repository;

import com.vn.backend.model.ThietBi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThietBiRepository extends JpaRepository<ThietBi, Long> {

    List<ThietBi> findByDeleteFlag(int deleteFlag, Pageable pagging);

    List<ThietBi> findByDeleteFlagOrderByTenThietBiAscMaThietBiAsc(Integer deleteFlag);

    ThietBi findByIdAndDeleteFlag(Long id, int deleteFlag);

    List<ThietBi> findByMaThietBiAndDeleteFlag(String maThietBi, Integer deleteFlag);

    List<ThietBi> findByTenThietBiAndDeleteFlag(String tenThietBi, Integer deleteFlag);

}
