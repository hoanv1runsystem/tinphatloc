package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ChiPhiDto;
import com.vn.backend.model.ChiPhi;
import com.vn.backend.repository.ChiPhiRepository;
import com.vn.backend.repository.custom.ChiPhiCustomRepository;
import com.vn.backend.response.ChiPhiResponse;
import com.vn.backend.service.ChiPhiService;
import com.vn.utils.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChiPhiServiceImpl implements ChiPhiService {

    @Autowired
    private ChiPhiRepository chiPhiRepository;

    @Autowired
    private ChiPhiCustomRepository chiPhiCustomRepository;

    @Override
    public ChiPhiResponse search(String keyword, Integer deleteFlag, Pageable pagging) {
        ChiPhiResponse results = new ChiPhiResponse();

        Page<ChiPhiDto> pages = chiPhiCustomRepository.search(keyword, deleteFlag, pagging);
        results.setChiPhis(pages.getContent());
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public ChiPhiDto getDetail(Long id, Integer deleteFlag) {
        ChiPhi detail = chiPhiRepository.findByIdAndDeleteFlag(id, deleteFlag);
        ChiPhiDto dto = new ChiPhiDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public ChiPhiDto add(ChiPhiDto dto) {
        ChiPhi object = new ChiPhi();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        ChiPhi result = chiPhiRepository.save(object);
        BeanUtils.copyProperties(result, dto);
        return dto;
    }

    @Override
    public ChiPhiDto update(ChiPhiDto dto) throws Exception {
        Optional<ChiPhi> option = chiPhiRepository.findById(dto.getId());

        if (option.isPresent()) {
            ChiPhi object = option.get();
            BeanUtils.copyProperties(dto, object);

            ChiPhi result = chiPhiRepository.save(object);
            BeanUtils.copyProperties(dto, result);
            return dto;
        } else {
            throw new Exception(Message.UPDATE_FAILURE);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<ChiPhi> option = chiPhiRepository.findById(id);

        if (option.isPresent()) {
            chiPhiRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
