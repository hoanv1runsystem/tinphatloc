package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.CapNhienLieuDto;
import com.vn.backend.model.CapNhienLieu;
import com.vn.backend.model.NhienLieu;
import com.vn.backend.repository.CapNhienLieuRepository;
import com.vn.backend.repository.NhienLieuRepository;
import com.vn.backend.repository.custom.CapNhienLieuCustomRepository;
import com.vn.backend.response.CapNhienLieuResponse;
import com.vn.backend.service.CapNhienLieuService;
import com.vn.utils.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CapNhienLieuServiceImpl implements CapNhienLieuService {

    @Autowired
    private CapNhienLieuRepository capNhienLieuRepository;

    @Autowired
    private CapNhienLieuCustomRepository capNhienLieuCustomRepository;

    @Autowired
    private NhienLieuRepository nhienLieuRepository;

    @Override
    public CapNhienLieuResponse search(String keyword, Integer deleteFlag, Pageable pagging) {
        CapNhienLieuResponse results = new CapNhienLieuResponse();

        Page<CapNhienLieuDto> pages = capNhienLieuCustomRepository.search(keyword, deleteFlag, pagging);
        results.setCapNhienLieus(pages.getContent());
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public CapNhienLieuDto getDetail(Long id, Integer deleteFlag) {
        CapNhienLieu detail = capNhienLieuRepository.findByIdAndDeleteFlag(id, deleteFlag);
        CapNhienLieuDto dto = new CapNhienLieuDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public CapNhienLieuDto add(CapNhienLieuDto dto) {

        List<NhienLieu> nhienLieus = nhienLieuRepository.findByMaNhienLieuAndDeleteFlag(dto.getMaNhienLieu(), CommonConstant.DELETE_FLAG_ENABLE);
        if (!Objects.isNull(nhienLieus) && !nhienLieus.isEmpty()) {
            NhienLieu nl = nhienLieus.get(0);
            nl.setTonKho(nl.getTonKho().subtract(dto.getSoLuong()));
            nhienLieuRepository.save(nl);
        }
        CapNhienLieu object = new CapNhienLieu();
        BeanUtils.copyProperties(dto, object);

        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        CapNhienLieu result = capNhienLieuRepository.save(object);
        BeanUtils.copyProperties(result, dto);
        return dto;
    }

    @Override
    public CapNhienLieuResponse addValidate(CapNhienLieuDto dto) {
        //		CustomerResponse result = new CustomerResponse();
        //		List<Category> categories = customerRepository.findByNameAndDeleteFlag(categoryDto.getName(),
        //				Constant.DELETE_FLAG_ACTIVE);
        //		if (categories != null && categories.size() > 0) {
        //			result.setStatus(Constant.DUPLICATE);
        //			result.setMessage(Message.NAME_CATEGORY_DUPLICATE);
        //			result.setCategoryDto(categoryDto);
        //			return result;
        //		}
        //
        //		Category category = new Category();
        //		BeanUtils.copyProperties(categoryDto, category);
        //		Category result = categoryRepository.save(category);
        //		BeanUtils.copyProperties(result, categoryDto);
        //
        //		categoryResponse.setStatus(Constant.STATUS_SUCCSESS);
        //		categoryResponse.setMessage(Message.ADD_SUCCESS);
        //		categoryResponse.setCategoryDto(categoryDto);
        //
        //		return categoryResponse;

        return null;
    }

    @Override
    public CapNhienLieuDto update(CapNhienLieuDto dto) throws Exception {
        Optional<CapNhienLieu> option = capNhienLieuRepository.findById(dto.getId());

        if (option.isPresent()) {
            List<NhienLieu> nhienLieus = nhienLieuRepository.findByMaNhienLieuAndDeleteFlag(dto.getMaNhienLieu(), CommonConstant.DELETE_FLAG_ENABLE);

            if (!Objects.isNull(nhienLieus) && !nhienLieus.isEmpty()) {
                NhienLieu nl = nhienLieus.get(0);
                nl.setTonKho(nl.getTonKho().add(dto.getSoLuong().subtract(option.get().getSoLuong())));
                nhienLieuRepository.save(nl);
            }

            CapNhienLieu object = option.get();
            BeanUtils.copyProperties(dto, object);

            CapNhienLieu result = capNhienLieuRepository.save(object);
            BeanUtils.copyProperties(dto, result);
            return dto;
        } else {
            throw new Exception(Message.UPDATE_FAILURE);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<CapNhienLieu> option = capNhienLieuRepository.findById(id);

        if (option.isPresent()) {
            capNhienLieuRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
