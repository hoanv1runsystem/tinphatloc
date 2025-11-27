package com.vn.backend.service;

import com.vn.backend.dto.NhapNhienLieuDto;
import com.vn.backend.response.NhapNhienLieuResponse;
import org.springframework.data.domain.Pageable;

public interface NhapNhienLieuService {

    NhapNhienLieuResponse search(String keyword, Integer deleteFlag, Pageable pagging);

    NhapNhienLieuDto getDetail(Long id, Integer deleteFlag);

    NhapNhienLieuResponse add(NhapNhienLieuDto dto);

    NhapNhienLieuResponse update(NhapNhienLieuDto dto);

    boolean delete(Long id) throws Exception;
}
