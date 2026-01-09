package com.vn.backend.service;

import com.vn.backend.dto.NhatKyXeDto;
import com.vn.backend.response.NhatKyXeResponse;
import org.springframework.data.domain.Pageable;

public interface NhatKyXeService {
    NhatKyXeResponse search(String keyword, Integer deleteFlag, Pageable pagging);

    NhatKyXeDto getDetail(Long id, Integer deleteFlag);

    NhatKyXeDto add(NhatKyXeDto dto);

    NhatKyXeDto update(NhatKyXeDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
