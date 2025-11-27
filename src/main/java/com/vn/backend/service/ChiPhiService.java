package com.vn.backend.service;

import com.vn.backend.dto.ChiPhiDto;
import com.vn.backend.response.ChiPhiResponse;
import org.springframework.data.domain.Pageable;

public interface ChiPhiService {
    ChiPhiResponse search(String keyword, Integer deleteFlag, Pageable pagging);

    ChiPhiDto getDetail(Long id, Integer deleteFlag);

    ChiPhiDto add(ChiPhiDto dto);

    ChiPhiDto update(ChiPhiDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
