package com.vn.backend.repository.custom;

import com.vn.backend.dto.ThietBiDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThietBiCustomRepository {

    Page<ThietBiDto> search(String keyword, int deleteFlag, Pageable pageable);
}
