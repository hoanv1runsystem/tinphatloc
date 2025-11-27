package com.vn.backend.service;

import com.vn.backend.dto.ReportDto;
import com.vn.backend.dto.RequestReportDto;
import com.vn.backend.response.ExportResponse;

import java.util.List;

public interface ReportService {
    List<ReportDto> findAll(RequestReportDto dto, Integer deleteFlag);

    ExportResponse viewExport(RequestReportDto dto, Integer deleteFlag);

    byte[] exportFileMutiCondition(RequestReportDto dto, Integer deleteFlag);

    byte[] exportFileCondition(RequestReportDto dto, Integer deleteFlag);
}
