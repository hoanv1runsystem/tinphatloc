package com.vn.backend.repository.custom;

import com.vn.backend.dto.CapNhienLieuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CapNhienLieuCustomRepository {

    Page<CapNhienLieuDto> search(String keyword, int deleteFlag, Pageable pageable);
}
