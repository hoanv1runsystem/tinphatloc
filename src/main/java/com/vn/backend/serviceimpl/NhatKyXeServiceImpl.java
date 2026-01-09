package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhatKyXeDto;
import com.vn.backend.model.NhatKyXe;
import com.vn.backend.repository.NhatKyXeRepository;
import com.vn.backend.repository.custom.NhatKyXeCustomRepository;
import com.vn.backend.response.NhatKyXeResponse;
import com.vn.backend.service.NhatKyXeService;
import com.vn.utils.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NhatKyXeServiceImpl implements NhatKyXeService {

    @Autowired
    private NhatKyXeRepository nhatKyXeRepository;

    @Autowired
    private NhatKyXeCustomRepository nhatKyXeCustomRepository;

    @Override
    public NhatKyXeResponse search(String keyword, Integer deleteFlag, Pageable pagging) {
        NhatKyXeResponse results = new NhatKyXeResponse();

        Page<NhatKyXeDto> pages = nhatKyXeCustomRepository.search(keyword, deleteFlag, pagging);

        results.setNhatKyXes(pages.getContent());
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public NhatKyXeDto getDetail(Long id, Integer deleteFlag) {
        NhatKyXe detail = nhatKyXeRepository.findByIdAndDeleteFlag(id, deleteFlag);
        NhatKyXeDto dto = new NhatKyXeDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public NhatKyXeDto add(NhatKyXeDto dto) {
        NhatKyXe object = new NhatKyXe();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        NhatKyXe result = nhatKyXeRepository.save(object);
        BeanUtils.copyProperties(result, dto);
        return dto;
    }

    @Override
    public NhatKyXeDto update(NhatKyXeDto dto) throws Exception {
        Optional<NhatKyXe> option = nhatKyXeRepository.findById(dto.getId());

        if (option.isPresent()) {
            NhatKyXe object = option.get();
            BeanUtils.copyProperties(dto, object);
            NhatKyXe result = nhatKyXeRepository.save(object);
            BeanUtils.copyProperties(result, dto);
            return dto;
        } else {
            throw new Exception(Message.UPDATE_FAILURE);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<NhatKyXe> option = nhatKyXeRepository.findById(id);

        if (option.isPresent()) {
            nhatKyXeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
