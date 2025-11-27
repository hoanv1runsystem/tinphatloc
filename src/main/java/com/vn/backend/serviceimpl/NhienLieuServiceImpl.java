package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhienLieuDto;
import com.vn.backend.model.NhienLieu;
import com.vn.backend.repository.NhienLieuRepository;
import com.vn.backend.response.NhienLieuResponse;
import com.vn.backend.service.NhienLieuService;
import com.vn.utils.Constant;
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
public class NhienLieuServiceImpl implements NhienLieuService {

    @Autowired
    private NhienLieuRepository nhienLieuRepository;

    @Override
    public List<NhienLieuDto> getAllData(Integer deleteFlag) {
        List<NhienLieu> listData = nhienLieuRepository.findByDeleteFlagOrderByTenNhienLieuAscMaNhienLieuAsc(deleteFlag);
        List<NhienLieuDto> listResult = new ArrayList<>();
        for (NhienLieu obj : listData) {
            NhienLieuDto dto = new NhienLieuDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        return listResult;
    }

    @Override
    public NhienLieuResponse searchView(String keywword, Integer deleteFlag, Pageable pagging) {
        NhienLieuResponse results = new NhienLieuResponse();

        Page<NhienLieu> pages = nhienLieuRepository.searchView(keywword, deleteFlag, pagging);
        List<NhienLieu> listData = pages.getContent();
        List<NhienLieuDto> listResult = new ArrayList<>();
        for (NhienLieu obj : listData) {
            NhienLieuDto dto = new NhienLieuDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        results.setNhienLieus(listResult);
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public NhienLieuDto getDetail(Long id, Integer deleteFlag) {
        NhienLieu detail = nhienLieuRepository.findByIdAndDeleteFlag(id, deleteFlag);
        NhienLieuDto dto = new NhienLieuDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public NhienLieuResponse add(NhienLieuDto dto) {

        NhienLieuResponse response = new NhienLieuResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setNhienLieu(dto);
            return response;
        }

        NhienLieu object = new NhienLieu();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        NhienLieu result = nhienLieuRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setNhienLieu(dto);

        return response;

    }

    @Override
    public NhienLieuResponse addValidate(NhienLieuDto dto) {
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
    public NhienLieuResponse update(NhienLieuDto dto) throws Exception {

        NhienLieuResponse response = new NhienLieuResponse();

        String checkDuplicate = checkDupcate(dto, false);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setNhienLieu(dto);
            return response;
        }

        Optional<NhienLieu> option = nhienLieuRepository.findById(dto.getId());
        if (option.isPresent()) {
            NhienLieu object = option.get();
            BeanUtils.copyProperties(dto, object);
            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            NhienLieu result = nhienLieuRepository.save(object);
            BeanUtils.copyProperties(dto, result);

            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            response.setNhienLieu(dto);
        } else {
            response.setStatus(Constant.NOT_DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Nhiên Liệu"));
            response.setNhienLieu(dto);
            return response;
        }

        return response;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<NhienLieu> option = nhienLieuRepository.findById(id);

        if (option.isPresent()) {
            nhienLieuRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check duplicate mã và tên
     * @param dto input
     * @param isAdd insert true, update = false
     * @return message error
     */
    private String checkDupcate(NhienLieuDto dto, Boolean isAdd) {

        List<NhienLieu> checkMa = nhienLieuRepository.findByMaNhienLieuAndDeleteFlag(dto.getMaNhienLieu(),
            Constant.DELETE_FLAG_ACTIVE);
        if (checkMa != null && checkMa.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Mã Nhiên Liệu");

        }

        List<NhienLieu> checkName = nhienLieuRepository.findByTenNhienLieuAndDeleteFlag(dto.getTenNhienLieu(),
            Constant.DELETE_FLAG_ACTIVE);

        if (checkName != null && checkName.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Tên Nhiên Liệu");
        }

        if (isAdd) {
            if (checkMa != null && !checkMa.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Mã Nhiên Liệu");

            }
            if (checkName != null && !checkName.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Tên Nhiên Liệu");
            }
        } else {
            for (NhienLieu d : checkMa) {
                if (Objects.equals(dto.getMaNhienLieu(), d.getMaNhienLieu())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Mã Nhiên Liệu");
                }
            }

            for (NhienLieu d : checkName) {
                if (Objects.equals(dto.getTenNhienLieu(), d.getTenNhienLieu())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Tên Nhiên Liệu");
                }
            }
        }

        if (!Objects.isNull(dto.getId())) {
            NhienLieu checkExits = nhienLieuRepository.findByIdAndDeleteFlag(dto.getId(), Constant.DELETE_FLAG_ACTIVE);

            if (Objects.isNull(checkExits)) {
                return "Nhiên Liệu Không tồn Tại";
            }
        }

        return null;
    }

}
