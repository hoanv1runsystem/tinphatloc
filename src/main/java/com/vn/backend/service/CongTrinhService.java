package com.vn.backend.service;

import com.vn.backend.dto.CongTrinhDto;
import com.vn.backend.response.CongTrinhResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CongTrinhService {
    CongTrinhResponse getPagging(Integer deleteFlag, Pageable pagging);

    CongTrinhResponse getPaggingAll(String keyword, Integer deleteFlag, Pageable pagging);

    List<CongTrinhDto> getAllData(Integer deleteFlag);

    CongTrinhDto getDetail(Long id, Integer deleteFlag);

    CongTrinhResponse add(CongTrinhDto dto);

    CongTrinhResponse addValidate(CongTrinhDto dto);

    CongTrinhResponse update(CongTrinhDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
