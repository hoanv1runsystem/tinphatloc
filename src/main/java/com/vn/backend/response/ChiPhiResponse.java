package com.vn.backend.response;

import com.vn.backend.dto.ChiPhiDto;
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
public class ChiPhiResponse extends BaseResponse {
    private ChiPhiDto chiPhi = new ChiPhiDto();
    private List<ChiPhiDto> chiPhis = new ArrayList<>();

}
