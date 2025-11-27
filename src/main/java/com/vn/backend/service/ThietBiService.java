package com.vn.backend.service;

import com.vn.backend.dto.ThietBiDto;
import com.vn.backend.response.ThietBiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThietBiService {
    List<ThietBiDto> getAllData(Integer deleteFlag);

    ThietBiResponse search(String keyword, Integer deleteFlag, Pageable pagging);

    ThietBiDto getDetail(Long id, Integer deleteFlag);

    ThietBiResponse add(ThietBiDto dto);

    ThietBiResponse addValidate(ThietBiDto dto);

    ThietBiResponse update(ThietBiDto dto) throws Exception;

    boolean delete(Long id) throws Exception;
}
