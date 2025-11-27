package com.vn.backend.service;

import com.vn.backend.dto.NhanVienDto;
import com.vn.backend.response.NhanVienResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NhanVienService {
    List<NhanVienDto> getAllData(Integer deleteFlag);

    NhanVienResponse getPagging(String keyword, Integer deleteFlag, Pageable pagging);

    NhanVienDto getDetail(Long id, Integer deleteFlag);

    NhanVienResponse add(NhanVienDto dto);

    NhanVienResponse update(NhanVienDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
