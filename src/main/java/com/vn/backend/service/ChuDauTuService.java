package com.vn.backend.service;

import com.vn.backend.dto.ChuDauTuDto;
import com.vn.backend.response.ChuDauTuResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChuDauTuService {

    List<ChuDauTuDto> getAllData(Integer deleteFlag);

    ChuDauTuResponse searchView(String keyword, Integer deleteFlag, Pageable pagging);

    ChuDauTuDto getDetail(Long id, Integer deleteFlag);

    ChuDauTuResponse add(ChuDauTuDto dto);

    ChuDauTuResponse addValidate(ChuDauTuDto dto);

    ChuDauTuResponse update(ChuDauTuDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
