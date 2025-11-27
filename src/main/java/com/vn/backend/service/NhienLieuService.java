package com.vn.backend.service;

import com.vn.backend.dto.NhienLieuDto;
import com.vn.backend.response.NhienLieuResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NhienLieuService {

    List<NhienLieuDto> getAllData(Integer deleteFlag);

    NhienLieuResponse searchView(String keyword, Integer deleteFlag, Pageable pagging);

    NhienLieuDto getDetail(Long id, Integer deleteFlag);

    NhienLieuResponse add(NhienLieuDto dto);

    NhienLieuResponse addValidate(NhienLieuDto dto);

    NhienLieuResponse update(NhienLieuDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
