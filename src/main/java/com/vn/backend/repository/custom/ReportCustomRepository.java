package com.vn.backend.repository.custom;

import com.vn.backend.dto.ExportChiPhiDto;
import com.vn.backend.dto.ExportNhatKyXeDto;
import com.vn.backend.dto.RequestExportDto;

import java.util.List;

public interface ReportCustomRepository {

    List<ExportNhatKyXeDto> findAll(RequestExportDto dto, Integer deleteFlag);

    List<ExportChiPhiDto> exportChiPhi(RequestExportDto request, Integer deleteFlag);
}
