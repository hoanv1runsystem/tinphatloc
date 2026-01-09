package com.vn.backend.controller;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.RequestExportDto;
import com.vn.backend.response.ExportNhatKyXeResponse;
import com.vn.backend.service.CongTrinhService;
import com.vn.backend.service.KhachHangService;
import com.vn.backend.service.NhanVienService;
import com.vn.backend.service.ExportNhatKyXeService;
import com.vn.backend.service.ThietBiService;
import com.vn.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class ExportNhatKyXeController {

    @Autowired
    private ExportNhatKyXeService reportService;

    @Autowired
    private ThietBiService thietBiService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private CongTrinhService congTrinhService;

    @RequestMapping(value = "/export/nhatKyXe", method = RequestMethod.GET)
    public String view(Model model) {
        LocalDate dateNow = LocalDate.now();
        RequestExportDto dto = new RequestExportDto();
        dto.setNgayChungTuFrom(LocalDate.of(dateNow.getYear(), dateNow.getMonth(), 1));
        dto.setNgayChungTuTo(LocalDate.now());

        ExportNhatKyXeResponse response = reportService.viewExport(dto, CommonConstant.DELETE_FLAG_ENABLE);

        dto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        dto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        dto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        dto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("export", dto);
        model.addAttribute("exportDatas", response);
        return "/backend/export/viewNhatKyXe";

    }

    @RequestMapping(value = "/export/nhatKyXe", method = RequestMethod.POST)
    public String doView(RequestExportDto dto, Model model) {

        //List<ExportNhatKyXeDto> response = reportService.findAll(dto, CommonConstant.DELETE_FLAG_ENABLE);
        ExportNhatKyXeResponse response = reportService.viewExport(dto, CommonConstant.DELETE_FLAG_ENABLE);
        dto.setThietBis(thietBiService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        dto.setKhachHangs(khachHangService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        dto.setCongTrinhs(congTrinhService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        dto.setNhanViens(nhanVienService.getAllData(CommonConstant.DELETE_FLAG_ENABLE));
        model.addAttribute("export", dto);

        model.addAttribute("exportDatas", response);
        return "/backend/export/viewNhatKyXe";

    }

    @RequestMapping(value = "/export/nhatKyXe/muti-condition", method = RequestMethod.POST)
    public ResponseEntity<byte[]> mutiCondition(RequestExportDto dto, Model model) {

        byte[] bytes = reportService.exportFileMutiCondition(dto, CommonConstant.DELETE_FLAG_ENABLE);

        String fileName = "Bang_Nhat_Ky";
        if (!Objects.isNull(dto.getNgayChungTuFrom())) {
            fileName = fileName + "_" + DateUtils.formatDate(dto.getNgayChungTuFrom(), DateUtils.DDMMYYYY);
        }

        if (!Objects.isNull(dto.getNgayChungTuTo())) {
            fileName = fileName + "_" + DateUtils.formatDate(dto.getNgayChungTuTo(), DateUtils.DDMMYYYY);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
            .filename(fileName + ".xlsx")
            .build());

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/export/nhatKyXe/condition", method = RequestMethod.POST)
    public ResponseEntity<byte[]> report(RequestExportDto dto, Model model) {

        byte[] bytes = reportService.exportFileCondition(dto, CommonConstant.DELETE_FLAG_ENABLE);

        String fileName = "Bang_Nhat_Ky";
        if (!Objects.isNull(dto.getNgayChungTuFrom())) {
            fileName = fileName + "_" + DateUtils.formatDate(dto.getNgayChungTuFrom(), DateUtils.DDMMYYYY);
        }

        if (!Objects.isNull(dto.getNgayChungTuTo())) {
            fileName = fileName + "_" + DateUtils.formatDate(dto.getNgayChungTuTo(), DateUtils.DDMMYYYY);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
            .filename(fileName + ".xlsx")
            .build());

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
