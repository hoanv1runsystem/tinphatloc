package com.vn.backend.response;

import com.vn.backend.dto.CongTrinhDto;
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
public class CongTrinhResponse extends BaseResponse {
    private CongTrinhDto congTrinh = new CongTrinhDto();
    private List<CongTrinhDto> congTrinhs = new ArrayList<>();

}
