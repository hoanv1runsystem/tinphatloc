package com.vn.backend.service;

import com.vn.backend.dto.KhachHangDto;
import com.vn.backend.response.KhachHangResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhachHangService {
    KhachHangResponse searchView(String keyword, Integer deleteFlag, Pageable pagging);

    KhachHangDto getDetail(Long id, Integer deleteFlag);

    KhachHangResponse add(KhachHangDto dto);

    KhachHangResponse addValidate(KhachHangDto dto);

    KhachHangResponse update(KhachHangDto dto) throws Exception;

    boolean delete(Long id) throws Exception;

    List<KhachHangDto> getAllData(Integer deleteFlag);
}
