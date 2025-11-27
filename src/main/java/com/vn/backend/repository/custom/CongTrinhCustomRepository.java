package com.vn.backend.repository.custom;

import com.vn.backend.dto.CongTrinhDto;
import com.vn.backend.dto.ReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CongTrinhCustomRepository {

    Page<CongTrinhDto> findAllCongTrinh(String keyword, int deleteFlag, Pageable pageable);



}
