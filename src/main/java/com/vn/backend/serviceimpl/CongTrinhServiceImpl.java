package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.CongTrinhDto;
import com.vn.backend.model.CongTrinh;
import com.vn.backend.repository.CongTrinhRepository;
import com.vn.backend.repository.custom.CongTrinhCustomRepository;
import com.vn.backend.response.CongTrinhResponse;
import com.vn.backend.service.CongTrinhService;
import com.vn.backend.service.KhachHangService;
import com.vn.utils.Constant;
import com.vn.utils.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CongTrinhServiceImpl implements CongTrinhService {

    @Autowired
    private CongTrinhRepository congTrinhRepository;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private CongTrinhCustomRepository congTrinhCustomRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public CongTrinhResponse getPagging(Integer deleteFlag, Pageable pagging) {
        CongTrinhResponse results = new CongTrinhResponse();

        Page<CongTrinh> pages = congTrinhRepository.findAll(pagging);
        List<CongTrinh> listResult = pages.getContent();
        results.setCongTrinhs(null);
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public CongTrinhResponse getPaggingAll(String keyword, Integer deleteFlag, Pageable pagging) {
        CongTrinhResponse results = new CongTrinhResponse();

        Page<CongTrinhDto> pages = congTrinhCustomRepository.findAllCongTrinh(keyword, deleteFlag, pagging);

        List<CongTrinhDto> listResult = pages.getContent();


        results.setCongTrinhs(listResult);
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public List<CongTrinhDto> getAllData(Integer deleteFlag) {
        List<CongTrinh> listData = congTrinhRepository.findByDeleteFlagOrderByTenCongTrinhAscMaCongTrinhAsc(deleteFlag);
        List<CongTrinhDto> listResult = new ArrayList<>();
        for (CongTrinh obj : listData) {
            CongTrinhDto dto = new CongTrinhDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        return listResult;
    }

    @Override
    public CongTrinhDto getDetail(Long id, Integer deleteFlag) {
        CongTrinh detail = congTrinhRepository.findByIdAndDeleteFlag(id, deleteFlag);
        CongTrinhDto dto = new CongTrinhDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public CongTrinhResponse add(CongTrinhDto dto) {
        CongTrinhResponse response = new CongTrinhResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setCongTrinh(dto);
            return response;
        }

        CongTrinh object = new CongTrinh();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        CongTrinh result = congTrinhRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setCongTrinh(dto);

        return response;
    }

    @Override
    public CongTrinhResponse addValidate(CongTrinhDto dto) {
        CongTrinhResponse response = new CongTrinhResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setCongTrinh(dto);
            return response;
        }

        CongTrinh object = new CongTrinh();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        CongTrinh result = congTrinhRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setCongTrinh(dto);

        return response;
    }

    @Override
    public CongTrinhResponse update(CongTrinhDto dto) throws Exception {
        CongTrinhResponse response = new CongTrinhResponse();

        String checkDuplicate = checkDupcate(dto, false);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setCongTrinh(dto);
            return response;
        }

        Optional<CongTrinh> option = congTrinhRepository.findById(dto.getId());
        if (option.isPresent()) {
            CongTrinh object = option.get();
            BeanUtils.copyProperties(dto, object);
            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            CongTrinh result = congTrinhRepository.save(object);
            BeanUtils.copyProperties(dto, result);

            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            response.setCongTrinh(dto);
        } else {
            response.setStatus(Constant.NOT_DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Công Trình"));
            response.setCongTrinh(dto);
            return response;
        }

        return response;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<CongTrinh> option = congTrinhRepository.findById(id);

        if (option.isPresent()) {
            congTrinhRepository.deleteById(id);
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
    private String checkDupcate(CongTrinhDto dto, Boolean isAdd) {

        List<CongTrinh> checkMa = congTrinhRepository.findByMaCongTrinhAndDeleteFlag(dto.getMaCongTrinh(),
            Constant.DELETE_FLAG_ACTIVE);
        if (checkMa != null && checkMa.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Mã Công Trình");

        }

        List<CongTrinh> checkName = congTrinhRepository.findByTenCongTrinhAndDeleteFlag(dto.getTenCongTrinh(),
            Constant.DELETE_FLAG_ACTIVE);

        if (checkName != null && checkName.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Tên Công Trình");
        }

        if (isAdd) {
            if (checkMa != null && !checkMa.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Mã Công Trình");

            }
            if (checkName != null && !checkName.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Tên Công Trình");
            }
        } else {
            for (CongTrinh d : checkMa) {
                if (Objects.equals(dto.getMaCongTrinh(), d.getMaCongTrinh())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Mã Công Trình");
                }
            }

            for (CongTrinh d : checkName) {
                if (Objects.equals(dto.getTenCongTrinh(), d.getTenCongTrinh())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Tên Công Trình");
                }
            }
        }

        if (!Objects.isNull(dto.getId())) {
            CongTrinh checkExits = congTrinhRepository.findByIdAndDeleteFlag(dto.getId(), Constant.DELETE_FLAG_ACTIVE);

            if (Objects.isNull(checkExits)) {
                return "Công Trình Không tồn Tại";
            }
        }

        return null;
    }

}
