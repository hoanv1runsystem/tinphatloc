package com.vn.backend.repository.custom;

import com.vn.backend.dto.NhatKyXeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NhatKyXeCustomRepository {

    Page<NhatKyXeDto> search(String keyword, Integer deleteFlag, Pageable pageable);
}
