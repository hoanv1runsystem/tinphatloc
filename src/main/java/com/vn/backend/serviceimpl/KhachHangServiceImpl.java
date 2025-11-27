package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.KhachHangDto;
import com.vn.backend.model.KhachHang;
import com.vn.backend.repository.KhachHangRepository;
import com.vn.backend.response.KhachHangResponse;
import com.vn.backend.service.KhachHangService;
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
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public KhachHangResponse searchView(String keyword, Integer deleteFlag, Pageable pagging) {
        KhachHangResponse results = new KhachHangResponse();

        Page<KhachHang> pages = khachHangRepository.searchView(keyword, deleteFlag, pagging);
        List<KhachHang> listData = pages.getContent();
        List<KhachHangDto> listResult = new ArrayList<>();
        for (KhachHang obj : listData) {
            KhachHangDto dto = new KhachHangDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        results.setKhachHangs(listResult);
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public KhachHangDto getDetail(Long id, Integer deleteFlag) {
        KhachHang detail = khachHangRepository.findByIdAndDeleteFlag(id, deleteFlag);
        KhachHangDto dto = new KhachHangDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public KhachHangResponse add(KhachHangDto dto) {

        KhachHangResponse response = new KhachHangResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setKhachHang(dto);
            return response;
        }

        KhachHang object = new KhachHang();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        KhachHang result = khachHangRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setKhachHang(dto);

        return response;
    }

    @Override
    public KhachHangResponse addValidate(KhachHangDto dto) {
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
    public KhachHangResponse update(KhachHangDto dto) throws Exception {
        KhachHangResponse response = new KhachHangResponse();

        String checkDuplicate = checkDupcate(dto, false);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setKhachHang(dto);
            return response;
        }

        Optional<KhachHang> option = khachHangRepository.findById(dto.getId());
        if (option.isPresent()) {
            KhachHang object = option.get();
            BeanUtils.copyProperties(dto, object);
            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            KhachHang result = khachHangRepository.save(object);
            BeanUtils.copyProperties(dto, result);

            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            response.setKhachHang(dto);
        } else {
            response.setStatus(Constant.NOT_DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Khách Hàng"));
            response.setKhachHang(dto);
            return response;
        }

        return response;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<KhachHang> option = khachHangRepository.findById(id);

        if (option.isPresent()) {
            khachHangRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<KhachHangDto> getAllData(Integer deletFlag) {
        List<KhachHang> listData = khachHangRepository.findByDeleteFlagOrderByTenKhachHangAscMaKhachHangAsc(deletFlag);
        List<KhachHangDto> listResult = new ArrayList<>();
        for (KhachHang obj : listData) {
            KhachHangDto dto = new KhachHangDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        return listResult;
    }

    /**
     * Check duplicate mã và tên
     * @param dto input
     * @param isAdd insert true, update = false
     * @return message error
     */
    private String checkDupcate(KhachHangDto dto, Boolean isAdd) {

        List<KhachHang> checkMa = khachHangRepository.findByMaKhachHangAndDeleteFlag(dto.getMaKhachHang(),
            Constant.DELETE_FLAG_ACTIVE);
        if (checkMa != null && checkMa.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Mã Nhân Viên");

        }

        /*List<KhachHang> checkName = nhanVienRepository.findByTenKhachHangAndDeleteFlag(dto.getTenNhienLieu(),
            Constant.DELETE_FLAG_ACTIVE);

        if (checkName != null && checkName.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Tên Nhân Viên");
        }*/

        if (isAdd) {
            if (checkMa != null && !checkMa.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Mã Khách Hàng");

            }
           /* if (checkName != null && !checkName.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Tên Khách Hàng");
            }*/
        } else {
            for (KhachHang d : checkMa) {
                if (Objects.equals(dto.getMaKhachHang(), d.getMaKhachHang())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Mã Khách Hàng");
                }
            }

        }

        if (!Objects.isNull(dto.getId())) {
            KhachHang checkExits = khachHangRepository.findByIdAndDeleteFlag(dto.getId(), Constant.DELETE_FLAG_ACTIVE);

            if (Objects.isNull(checkExits)) {
                return "Khách Hàng Không tồn Tại";
            }
        }

        return null;
    }
}
