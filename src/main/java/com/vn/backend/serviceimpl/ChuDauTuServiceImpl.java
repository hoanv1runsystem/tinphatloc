package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ChuDauTuDto;
import com.vn.backend.model.ChuDauTu;
import com.vn.backend.repository.ChuDauTuRepository;
import com.vn.backend.response.ChuDauTuResponse;
import com.vn.backend.service.ChuDauTuService;
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
public class ChuDauTuServiceImpl implements ChuDauTuService {

    @Autowired
    private ChuDauTuRepository chuDauTuRepository;

    public List<ChuDauTuDto> getAllData(Integer deleteFlag) {
        List<ChuDauTu> listData = chuDauTuRepository.findByDeleteFlagOrderByTenChuDauTuAscMaChuDauTuAsc(deleteFlag);
        List<ChuDauTuDto> listResult = new ArrayList<>();
        for (ChuDauTu obj : listData) {
            ChuDauTuDto dto = new ChuDauTuDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        return listResult;
    }

    @Override
    public ChuDauTuResponse searchView(String keyword, Integer deleteFlag, Pageable pagging) {
        ChuDauTuResponse results = new ChuDauTuResponse();

        Page<ChuDauTu> pages = chuDauTuRepository.searchView(keyword, deleteFlag, pagging);
        List<ChuDauTu> listData = pages.getContent();
        List<ChuDauTuDto> listResult = new ArrayList<>();
        for (ChuDauTu obj : listData) {
            ChuDauTuDto dto = new ChuDauTuDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        results.setChuDauTus(listResult);
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public ChuDauTuDto getDetail(Long id, Integer deleteFlag) {
        ChuDauTu detail = chuDauTuRepository.findByIdAndDeleteFlag(id, deleteFlag);
        ChuDauTuDto dto = new ChuDauTuDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public ChuDauTuResponse add(ChuDauTuDto dto) {
        ChuDauTuResponse response = new ChuDauTuResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setChuDauTu(dto);
            return response;
        }

        ChuDauTu object = new ChuDauTu();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        ChuDauTu result = chuDauTuRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setChuDauTu(dto);

        return response;
    }

    @Override
    public ChuDauTuResponse addValidate(ChuDauTuDto dto) {
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
    public ChuDauTuResponse update(ChuDauTuDto dto) throws Exception {

        ChuDauTuResponse response = new ChuDauTuResponse();

        String checkDuplicate = checkDupcate(dto, false);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setChuDauTu(dto);
            return response;
        }

        Optional<ChuDauTu> option = chuDauTuRepository.findById(dto.getId());
        if (option.isPresent()) {
            ChuDauTu object = option.get();
            BeanUtils.copyProperties(dto, object);
            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            ChuDauTu result = chuDauTuRepository.save(object);
            BeanUtils.copyProperties(dto, result);

            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            response.setChuDauTu(dto);
        } else {
            response.setStatus(Constant.NOT_DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Chủ Đầu Tư"));
            response.setChuDauTu(dto);
            return response;
        }

        return response;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<ChuDauTu> option = chuDauTuRepository.findById(id);

        if (option.isPresent()) {
            chuDauTuRepository.deleteById(id);
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
    private String checkDupcate(ChuDauTuDto dto, Boolean isAdd) {

        List<ChuDauTu> checkMa = chuDauTuRepository.findByMaChuDauTuAndDeleteFlag(dto.getMaChuDauTu(),
            Constant.DELETE_FLAG_ACTIVE);
        if (checkMa != null && checkMa.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Mã Chủ Đầu Tư");

        }

        List<ChuDauTu> checkName = chuDauTuRepository.findByTenChuDauTuAndDeleteFlag(dto.getTenChuDauTu(),
            Constant.DELETE_FLAG_ACTIVE);

        if (checkName != null && checkName.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Tên Chủ Đầu Tư");
        }

        if (isAdd) {
            if (checkMa != null && !checkMa.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Mã Chủ Đầu Tư");

            }
            if (checkName != null && !checkName.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Tên Chủ Đầu Tư");
            }
        } else {
            for (ChuDauTu d : checkMa) {
                if (Objects.equals(dto.getMaChuDauTu(), d.getMaChuDauTu())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Mã Chủ Đầu Tư");
                }
            }

            for (ChuDauTu d : checkName) {
                if (Objects.equals(dto.getTenChuDauTu(), d.getTenChuDauTu())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Tên Chủ Đầu Tư");
                }
            }
        }

        if (!Objects.isNull(dto.getId())) {
            ChuDauTu checkExits = chuDauTuRepository.findByIdAndDeleteFlag(dto.getId(), Constant.DELETE_FLAG_ACTIVE);

            if (Objects.isNull(checkExits)) {
                return "Chủ Đầu Tư Không tồn Tại";
            }
        }

        return null;
    }

}
