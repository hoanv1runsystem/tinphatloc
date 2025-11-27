package com.vn.backend.response;

import com.vn.backend.dto.ChuDauTuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChuDauTuResponse extends BaseResponse {
    private ChuDauTuDto chuDauTu = new ChuDauTuDto();
    private List<ChuDauTuDto> chuDauTus = new ArrayList<>();

}
