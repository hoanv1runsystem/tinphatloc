package com.vn.backend.repository.custom;

import com.vn.backend.dto.NhapNhienLieuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NhapNhienLieuCustomRepository {

    Page<NhapNhienLieuDto> search(String keyword, int deleteFlag, Pageable pageable);
}
