package com.vn.backend.repository.custom;

import com.vn.backend.dto.ReportDto;
import com.vn.backend.dto.RequestReportDto;

import java.util.List;

public interface ReportCustomRepository {

    // Page<CongTrinhDto> findAllCongTrinh(String keyword, int deleteFlag, Pageable pageable);
    List<ReportDto> findAll(RequestReportDto dto, int deleteFlag);
}
