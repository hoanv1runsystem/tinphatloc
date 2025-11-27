package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.NhanVienDto;
import com.vn.backend.model.NhanVien;
import com.vn.backend.repository.NhanVienRepository;
import com.vn.backend.response.NhanVienResponse;
import com.vn.backend.service.NhanVienService;
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
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public List<NhanVienDto> getAllData(Integer deleteFlag) {
        List<NhanVien> listData = nhanVienRepository.findByDeleteFlagOrderByTenNhanVienAscMaNhanVienAsc(deleteFlag);
        List<NhanVienDto> listResult = new ArrayList<>();
        for (NhanVien obj : listData) {
            NhanVienDto dto = new NhanVienDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        return listResult;
    }

    @Override
    public NhanVienResponse getPagging(String keyword, Integer deleteFlag, Pageable pagging) {
        NhanVienResponse results = new NhanVienResponse();

        Page<NhanVien> pages = nhanVienRepository.searchView(keyword, deleteFlag, pagging);

        List<NhanVien> listData = pages.getContent();
        List<NhanVienDto> listResult = new ArrayList<>();
        for (NhanVien obj : listData) {
            NhanVienDto dto = new NhanVienDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        results.setNhanViens(listResult);
        results.setPage(pages.getPageable().getPageNumber());
        results.setSize(pages.getSize());
        results.setTotalPages(pages.getTotalPages());
        results.setTotalElement(pages.getTotalElements());
        return results;
    }

    @Override
    public NhanVienDto getDetail(Long id, Integer deleteFlag) {
        NhanVien detail = nhanVienRepository.findByIdAndDeleteFlag(id, deleteFlag);
        NhanVienDto dto = new NhanVienDto();
        BeanUtils.copyProperties(detail, dto);
        return dto;
    }

    @Override
    public NhanVienResponse add(NhanVienDto dto) {
        NhanVienResponse response = new NhanVienResponse();

        String checkDuplicate = checkDupcate(dto, true);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setNhanVien(dto);
            return response;
        }

        NhanVien object = new NhanVien();
        BeanUtils.copyProperties(dto, object);
        object.setDeleteFlag(CommonConstant.DELETE_FLAG_ENABLE);
        NhanVien result = nhanVienRepository.save(object);
        BeanUtils.copyProperties(result, dto);

        response.setStatus(Constant.STATUS_SUCCSESS);
        response.setMessage(Message.ADD_SUCCESS);
        response.setNhanVien(dto);

        return response;
    }

    @Override
    public NhanVienResponse update(NhanVienDto dto) throws Exception {
        NhanVienResponse response = new NhanVienResponse();

        String checkDuplicate = checkDupcate(dto, false);
        if (checkDuplicate != null) {
            response.setStatus(Constant.DUPLICATE);
            response.setMessage(checkDuplicate);
            response.setNhanVien(dto);
            return response;
        }

        Optional<NhanVien> option = nhanVienRepository.findById(dto.getId());
        if (option.isPresent()) {
            NhanVien object = option.get();
            BeanUtils.copyProperties(dto, object);
            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            NhanVien result = nhanVienRepository.save(object);
            BeanUtils.copyProperties(dto, result);

            response.setStatus(Constant.STATUS_SUCCSESS);
            response.setMessage(Message.ADD_SUCCESS);
            response.setNhanVien(dto);
        } else {
            response.setStatus(Constant.NOT_DUPLICATE);
            response.setMessage(String.format(Message.NOT_EXIST, "Nhân Viên"));
            response.setNhanVien(dto);
            return response;
        }

        return response;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<NhanVien> option = nhanVienRepository.findById(id);

        if (option.isPresent()) {
            nhanVienRepository.deleteById(id);
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
    private String checkDupcate(NhanVienDto dto, Boolean isAdd) {

        List<NhanVien> checkMa = nhanVienRepository.findByMaNhanVienAndDeleteFlag(dto.getMaNhanVien(),
            Constant.DELETE_FLAG_ACTIVE);
        if (checkMa != null && checkMa.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Mã Nhân Viên");

        }

        /*List<NhanVien> checkName = nhanVienRepository.findByTenNhanVienAndDeleteFlag(dto.getTenNhienLieu(),
            Constant.DELETE_FLAG_ACTIVE);

        if (checkName != null && checkName.size() > 1) {
            return String.format(Message.NAME_DUPLICATE, "Tên Nhân Viên");
        }*/

        if (isAdd) {
            if (checkMa != null && !checkMa.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Mã Nhân Viên");

            }
            /*if (checkName != null && !checkName.isEmpty()) {
                return String.format(Message.NAME_DUPLICATE, "Tên Nhân Viên");
            }*/
        } else {
            for (NhanVien d : checkMa) {
                if (Objects.equals(dto.getMaNhanVien(), d.getMaNhanVien())
                    && !Objects.equals(dto.getId(), d.getId())) {
                    return String.format(Message.NAME_DUPLICATE, "Mã Nhân Viên");
                }
            }

        }

        if (!Objects.isNull(dto.getId())) {
            NhanVien checkExits = nhanVienRepository.findByIdAndDeleteFlag(dto.getId(), Constant.DELETE_FLAG_ACTIVE);

            if (Objects.isNull(checkExits)) {
                return "Nhân Viên Không tồn Tại";
            }
        }

        return null;
    }
}
