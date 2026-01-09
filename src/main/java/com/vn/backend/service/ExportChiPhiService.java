package com.vn.backend.service;

import com.vn.backend.dto.RequestExportDto;
import com.vn.backend.response.ExportChiPhiResponse;

public interface ExportChiPhiService {

    ExportChiPhiResponse viewChiPhi(RequestExportDto dto, Integer deleteFlag);

    byte[] exportFileMutiCondition(RequestExportDto dto, Integer deleteFlag);

    byte[] exportFileCondition(RequestExportDto dto, Integer deleteFlag);

}
