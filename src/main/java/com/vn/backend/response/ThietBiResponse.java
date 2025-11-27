package com.vn.backend.response;

import com.vn.backend.dto.ThietBiDto;
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
public class ThietBiResponse extends BaseResponse {
    private ThietBiDto thietBi = new ThietBiDto();
    private List<ThietBiDto> thietBis = new ArrayList<>();

}
