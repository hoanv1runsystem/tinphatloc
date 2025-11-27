package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ThietBiDto;
import com.vn.backend.model.ThietBi;
import com.vn.backend.repository.ThietBiRepository;
import com.vn.backend.repository.custom.ThietBiCustomRepository;
import com.vn.backend.response.ThietBiResponse;
import com.vn.backend.service.ThietBiService;
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
public class ThietBiServiceImpl implements ThietBiService {

    @Autowired
    private ThietBiRepository thietBiRepository;

    @Autowired
    private ThietBiCustomRepository thietBiCustomRepository;

    @Override
    public List<ThietBiDto> getAllData(Integer deleteFlag) {
        List<ThietBi> listData = thietBiRepository.findByDeleteFlagOrderByTenThietBiAscMaThietBiAsc(deleteFlag);
        List<ThietBiDto> listResult = new ArrayList<>();
        for (ThietBi obj : listData) {
            ThietBiDto dto = new ThietBiDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        return listResult;
    }

    @Override
    public ThietBiResponse search(String keyword, Integer deleteFlag, Pageable pagging) {
        ThietBiResponse results = new ThietBiResponse();

        Page<ThietBiDto> pages = thietBiCustomRepository.search(keyword, deleteFlag, pagging);
        List<ThietBiDto> listData = pages.getContent();
        results.setThietBis(listData);
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public ThietBiDto getDetail(Long id, Integer deleteFlag) {
        ThietBi detail = thietBiRepository.findByIdAndDeleteFlag(id, deleteFlag);
        ThietBiDto dto = new ThietBiDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public ThietBiResponse add(ThietBiDto dto) {
        ThietBiResponse result = new ThietBiResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            result.setStatus(Constant.DUPLICATE);
            result.setMessage(checkDuplicate);
            result.setThietBi(dto);
            return result;
        }

        ThietBi object = new ThietBi();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        ThietBi data = thietBiRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        result.setStatus(Constant.STATUS_SUCCSESS);
        result.setMessage(Message.ADD_SUCCESS);
        result.setThietBi(dto);

        return result;
    }

    @Override
    public ThietBiResponse addValidate(ThietBiDto dto) {
        ThietBiResponse result = new ThietBiResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            result.setStatus(Constant.DUPLICATE);
            result.setMessage(checkDuplicate);
            result.setThietBi(dto);
            return result;
        }

        ThietBi object = new ThietBi();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        ThietBi data = thietBiRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        result.setStatus(Constant.STATUS_SUCCSESS);
        result.setMessage(Message.ADD_SUCCESS);
        result.setThietBi(dto);

        return result;
    }

    @Override
    public ThietBiResponse update(ThietBiDto dto) throws Exception {

        ThietBiResponse response = new ThietBiResponse();

        String checkDuplicate = checkDupcate(dto, false);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setThietBi(dto);
            return response;
        }

        Optional<ThietBi> option = thietBiRepository.findById(dto.getId());
        if (option.isPresent()) {
            ThietBi object = option.get();
            BeanUtils.copyProperties(dto, object);
            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            ThietBi result = thietBiRepository.save(object);
            BeanUtils.copyProperties(dto, result);

            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            response.setThietBi(dto);
        } else {
            response.setStatus(Constant.NOT_DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Thiết Bị"));
            response.setThietBi(dto);
            return response;
        }

        return response;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<ThietBi> option = thietBiRepository.findById(id);

        if (option.isPresent()) {
            thietBiRepository.deleteById(id);
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
    private String checkDupcate(ThietBiDto dto, Boolean isAdd) {

        List<ThietBi> checkMa = thietBiRepository.findByMaThietBiAndDeleteFlag(dto.getMaThietBi(),
            Constant.DELETE_FLAG_ACTIVE);
        if (checkMa != null && checkMa.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Mã Công Trình");

        }

        List<ThietBi> checkName = thietBiRepository.findByTenThietBiAndDeleteFlag(dto.getTenThietBi(),
            Constant.DELETE_FLAG_ACTIVE);

        if (checkName != null && checkName.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Tên Thiết Bị");
        }

        if (isAdd) {
            if (checkMa != null && !checkMa.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Mã Thiết Bị");

            }
            if (checkName != null && !checkName.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Tên Thiết Bị");
            }
        } else {
            for (ThietBi d : checkMa) {
                if (Objects.equals(dto.getMaThietBi(), d.getMaThietBi())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Mã Thiết Bị");
                }
            }

            for (ThietBi d : checkName) {
                if (Objects.equals(dto.getTenThietBi(), d.getTenThietBi())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Tên Thiết Bị");
                }
            }
        }

        if (!Objects.isNull(dto.getId())) {
            ThietBi checkExits = thietBiRepository.findByIdAndDeleteFlag(dto.getId(), Constant.DELETE_FLAG_ACTIVE);

            if (Objects.isNull(checkExits)) {
                return "Thiết Bị Không tồn Tại";
            }
        }

        return null;
    }
}
