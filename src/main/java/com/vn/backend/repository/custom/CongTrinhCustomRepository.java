package com.vn.backend.repository.custom;

import com.vn.backend.dto.CongTrinhDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CongTrinhCustomRepository {

    Page<CongTrinhDto> findAllCongTrinh(String keyword, int deleteFlag, Pageable pageable);



}
