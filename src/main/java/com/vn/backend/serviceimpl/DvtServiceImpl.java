package com.vn.backend.serviceimpl;

import com.vn.backend.dto.DvtDto;
import com.vn.backend.model.Dvt;
import com.vn.backend.repository.DvtRepository;
import com.vn.backend.service.DvtService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DvtServiceImpl implements DvtService {

    @Autowired
    private DvtRepository dvtRepository;

    @Override
    public List<DvtDto> getAllData(Integer deleteFlag) {
        List<Dvt> listData = dvtRepository.findByDeleteFlagOrderByTenDvtAscMaDvtAsc(deleteFlag);
        List<DvtDto> listResult = new ArrayList<>();
        for (Dvt obj : listData) {
            DvtDto dto = new DvtDto();
            BeanUtils.copyProperties(obj, dto);
            listResult.add(dto);
        }
        if (listResult.isEmpty()) {
            DvtDto ca = new DvtDto();
            ca.setId(1L);
            ca.setMaDvt("CA");
            ca.setTenDvt("Ca");
            listResult.add(ca);
            DvtDto chuyen = new DvtDto();
            chuyen.setId(2L);
            chuyen.setMaDvt("CHUYEN");
            chuyen.setTenDvt("Chuyến");
            listResult.add(chuyen);
        }
        return listResult;
    }

    /**
     * Check duplicate mã và tên
     * @param dto input
     * @param isAdd insert true, update = false
     * @return message error
     */
    /*private String checkDupcate(NhienLieuDto dto, Boolean isAdd) {

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
    }*/

}
