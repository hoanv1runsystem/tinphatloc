package com.vn.backend.service;

import com.vn.backend.dto.DvtDto;
import com.vn.backend.dto.NhienLieuDto;
import com.vn.backend.response.NhienLieuResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DvtService {

    List<DvtDto> getAllData(Integer deleteFlag);


}
