package com.vn.backend.repository.custom;

import com.vn.backend.dto.ChiPhiDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChiPhiCustomRepository {

    Page<ChiPhiDto> search(String keyword, Integer deleteFlag, Pageable pageable);
}
