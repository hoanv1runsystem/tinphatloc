package com.vn.backend.service;

import com.vn.backend.dto.ExportNhatKyXeDto;
import com.vn.backend.dto.RequestExportDto;
import com.vn.backend.response.ExportNhatKyXeResponse;

import java.util.List;

public interface ExportNhatKyXeService {
    List<ExportNhatKyXeDto> findAll(RequestExportDto dto, Integer deleteFlag);

    ExportNhatKyXeResponse viewExport(RequestExportDto dto, Integer deleteFlag);

    byte[] exportFileMutiCondition(RequestExportDto dto, Integer deleteFlag);

    byte[] exportFileCondition(RequestExportDto dto, Integer deleteFlag);

}
