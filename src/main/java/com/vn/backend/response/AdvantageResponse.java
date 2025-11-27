package com.vn.backend.response;

import java.util.ArrayList;
import java.util.List;

import com.vn.backend.dto.AdvantageDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvantageResponse extends BaseResponse {
	private AdvantageDto advantage;
	private List<AdvantageDto> advantages = new ArrayList<>();
}
