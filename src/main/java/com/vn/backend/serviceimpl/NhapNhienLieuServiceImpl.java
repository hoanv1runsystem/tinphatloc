package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhapNhienLieuDto;
import com.vn.backend.model.NhapNhienLieu;
import com.vn.backend.model.NhienLieu;
import com.vn.backend.repository.NhapNhienLieuRepository;
import com.vn.backend.repository.NhienLieuRepository;
import com.vn.backend.repository.custom.NhapNhienLieuCustomRepository;
import com.vn.backend.response.NhapNhienLieuResponse;
import com.vn.backend.service.NhapNhienLieuService;
import com.vn.utils.Constant;
import com.vn.utils.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NhapNhienLieuServiceImpl implements NhapNhienLieuService {

    @Autowired
    private NhapNhienLieuRepository nhapNhienLieuRepository;

    @Autowired
    private NhapNhienLieuCustomRepository nhapNhienLieuCustomRepository;

    @Autowired
    private NhienLieuRepository nhienLieuRepository;

    @Override
    public NhapNhienLieuResponse search(String keyword, Integer deleteFlag, Pageable pagging) {
        NhapNhienLieuResponse results = new NhapNhienLieuResponse();

        Page<NhapNhienLieuDto> pages = nhapNhienLieuCustomRepository.search(keyword, deleteFlag, pagging);
        results.setNhapNhienLieus(pages.getContent());
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public NhapNhienLieuDto getDetail(Long id, Integer deleteFlag) {
        NhapNhienLieu detail = nhapNhienLieuRepository.findByIdAndDeleteFlag(id, deleteFlag);
        NhapNhienLieuDto dto = new NhapNhienLieuDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    @Transactional
    public NhapNhienLieuResponse add(NhapNhienLieuDto dto) {
        NhapNhienLieuResponse response = new NhapNhienLieuResponse();
        List<NhienLieu> nhienLieus = nhienLieuRepository.findByMaNhienLieuAndDeleteFlag(dto.getMaNhienLieu(), CommonConstant.DELETE_FLAG_ENABLE);

        if (nhienLieus.isEmpty()) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Dữ Liệu"));
            response.setNhapNhienLieu(dto);
            return response;
        }

        if (!Objects.isNull(nhienLieus) && nhienLieus.size() > 0) {
            NhienLieu nl = nhienLieus.get(0);
            nl.setTonKho(nl.getTonKho().add(dto.getSoLuong()));
            nhienLieuRepository.save(nl);
        }

        NhapNhienLieu object = new NhapNhienLieu();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        NhapNhienLieu result = nhapNhienLieuRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setNhapNhienLieu(dto);

        return response;
    }

    @Override
    @Transactional
    public NhapNhienLieuResponse update(NhapNhienLieuDto dto) {

        NhapNhienLieuResponse response = new NhapNhienLieuResponse();
        NhapNhienLieu object = nhapNhienLieuRepository.findByIdAndDeleteFlag(dto.getId(),
            CommonConstant.DELETE_FLAG_ENABLE);

        if (object != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Dữ Liệu"));
            response.setNhapNhienLieu(dto);
            return response;
        }

        List<NhienLieu> nhienLieus = nhienLieuRepository.findByMaNhienLieuAndDeleteFlag(dto.getMaNhienLieu(), CommonConstant.DELETE_FLAG_ENABLE);

        if (!Objects.isNull(nhienLieus) && !nhienLieus.isEmpty()) {
            NhienLieu nl = nhienLieus.get(0);
            nl.setTonKho(nl.getTonKho().add(dto.getSoLuong().subtract(object.getSoLuong())));
            nhienLieuRepository.save(nl);
        }

        BeanUtils.copyProperties(dto, object);
        NhapNhienLieu result = nhapNhienLieuRepository.save(object);
        BeanUtils.copyProperties(dto, result);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setNhapNhienLieu(dto);

        return response;

    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<NhapNhienLieu> option = nhapNhienLieuRepository.findById(id);

        if (option.isPresent()) {
            nhapNhienLieuRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
