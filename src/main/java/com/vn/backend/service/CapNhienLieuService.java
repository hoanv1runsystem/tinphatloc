package com.vn.backend.service;

import com.vn.backend.dto.CapNhienLieuDto;
import com.vn.backend.response.CapNhienLieuResponse;
import org.springframework.data.domain.Pageable;

public interface CapNhienLieuService {
    CapNhienLieuResponse search(String keyword, Integer deleteFlag, Pageable pagging);

    CapNhienLieuDto getDetail(Long id, Integer deleteFlag);

    CapNhienLieuDto add(CapNhienLieuDto dto);

    CapNhienLieuResponse addValidate(CapNhienLieuDto dto);

    CapNhienLieuDto update(CapNhienLieuDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
