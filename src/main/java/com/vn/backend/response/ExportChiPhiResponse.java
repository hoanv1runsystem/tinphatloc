package com.vn.backend.response;

import com.vn.backend.dto.ExportChiPhiDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExportChiPhiResponse {
    private List<ExportChiPhiDto> exports = new ArrayList<>();
    private BigDecimal tongTien = BigDecimal.ZERO;
    private String tongTienDisp = "0";
}
