package com.vn.backend.serviceimpl;

import com.vn.backend.constants.CommonConstant;
import com.vn.backend.dto.ReportDto;
import com.vn.backend.dto.RequestReportDto;
import com.vn.backend.model.CongTrinh;
import com.vn.backend.model.KhachHang;
import com.vn.backend.model.NhanVien;
import com.vn.backend.model.ThietBi;
import com.vn.backend.repository.CongTrinhRepository;
import com.vn.backend.repository.KhachHangRepository;
import com.vn.backend.repository.NhanVienRepository;
import com.vn.backend.repository.ThietBiRepository;
import com.vn.backend.repository.custom.ReportCustomRepository;
import com.vn.backend.response.ExportResponse;
import com.vn.backend.service.ReportService;
import com.vn.utils.DateUtils;
import com.vn.utils.ExcelUtils;
import com.vn.utils.FormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportCustomRepository reportCustomRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private ThietBiRepository thietBiRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private CongTrinhRepository congTrinhRepository;

    @Override
    public List<ReportDto> findAll(RequestReportDto dto, Integer deleteFlag) {

        return reportCustomRepository.findAll(dto, deleteFlag);
    }

    @Override
    public ExportResponse viewExport(RequestReportDto dto, Integer deleteFlag) {
        ExportResponse response = new ExportResponse();
        List<ReportDto> dtoList = reportCustomRepository.findAll(dto, deleteFlag);
        response.setReports(dtoList);
        if (!dtoList.isEmpty()) {
            BigDecimal tongSoChuyen = dtoList.stream()
                .map(ReportDto::getSoChuyen)        // lấy field cần cộng
                .filter(Objects::nonNull)          // tránh null pointer
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal tongSoKm = dtoList.stream()
                .map(ReportDto::getSoKm)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal tongSoTan = dtoList.stream()
                .map(ReportDto::getSoTan)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal tongKhoiLuong = dtoList.stream()
                .map(ReportDto::getKhoiLuong)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal tongSoGio = dtoList.stream()
                .map(ReportDto::getTongGio)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal tongSoCa = dtoList.stream()
                .map(ReportDto::getSoCa)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal tongTien = dtoList.stream()
                .map(ReportDto::getThanhTien)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            response.setTongSoChuyen(tongSoChuyen);
            response.setTongSoKm(tongSoKm);
            response.setTongSoTan(tongSoTan);
            response.setTongSoGio(tongSoGio);
            response.setTongKhoiLuong(tongKhoiLuong);
            response.setTongSoCa(tongSoCa);
            response.setTongTien(tongTien);

            response.setTongSoChuyenDisp(FormatUtils.formatBigDecimal(tongSoChuyen, 2));
            response.setTongSoKmDisp(FormatUtils.formatBigDecimal(tongSoKm, 2));
            response.setTongSoTanDisp(FormatUtils.formatBigDecimal(tongSoTan, 2));
            response.setTongSoGioDisp(FormatUtils.formatBigDecimal(tongSoGio, 2));
            response.setTongKhoiLuongDisp(FormatUtils.formatBigDecimal(tongKhoiLuong, 2));
            response.setTongSoCaDisp(FormatUtils.formatBigDecimal(tongSoCa, 2));
            response.setTongTienDisp(FormatUtils.formatBigDecimal(tongTien, 2));
        }

        return response;

    }

    @Override
    public byte[] exportFileMutiCondition(RequestReportDto dto, Integer deleteFlag) {
        ClassPathResource templateResource = new ClassPathResource("templates/export/export_temp.xlsx");
        try (InputStream templateStream = templateResource.getInputStream();
            Workbook workbook = new XSSFWorkbook(templateStream);
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheetNew = workbook.createSheet("TH_CHI_TIET");
            Sheet sheetTemp = workbook.getSheet("TMP_TONG_HOP");

            String title = getTitle(dto, "All");
            createDataSheetExport(dto, deleteFlag, workbook, sheetNew, sheetTemp, title);

            deleteSheet(workbook, "TMP_KHACH_HANG");
            deleteSheet(workbook, "TMP_NHAN_VIEN");
            deleteSheet(workbook, "TMP_CONG_TRINH");
            deleteSheet(workbook, "TMP_THIET_BI");
            deleteSheet(workbook, "TMP_TONG_HOP");
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] exportFileCondition(RequestReportDto dto, Integer deleteFlag) {
        ClassPathResource templateResource = new ClassPathResource("templates/export/export_temp.xlsx");
        try (InputStream templateStream = templateResource.getInputStream();
            Workbook workbook = new XSSFWorkbook(templateStream);
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheetTemp = workbook.getSheet("TMP_TONG_HOP");

            Sheet sheetNew = workbook.createSheet("TH_THIET_BI");
            String title = getTitle(dto, "thietBi");
            RequestReportDto input = new RequestReportDto();
            BeanUtils.copyProperties(dto, input);
            //input.setMaThietBi(null);
            input.setMaKhachHang(null);
            input.setCongTrinhs(null);
            input.setMaNhanVien(null);
            createDataSheetExport(input, deleteFlag, workbook, sheetNew, sheetTemp, title);

            sheetNew = workbook.createSheet("TH_KHACH_HANG");
            title = getTitle(dto, "khachHang");
            BeanUtils.copyProperties(dto, input);
            input.setMaThietBi(null);
            // input.setMaKhachHang(null);
            input.setCongTrinhs(null);
            input.setMaNhanVien(null);
            createDataSheetExport(input, deleteFlag, workbook, sheetNew, sheetTemp, title);

            sheetNew = workbook.createSheet("TH_CONG_TRINH");
            title = getTitle(dto, "congTrinh");

            BeanUtils.copyProperties(dto, input);
            input.setMaThietBi(null);
            input.setMaKhachHang(null);
            // input.setCongTrinhs(null);
            input.setMaNhanVien(null);
            createDataSheetExport(input, deleteFlag, workbook, sheetNew, sheetTemp, title);

            sheetNew = workbook.createSheet("TH_NHAN_VIEN");
            title = getTitle(dto, "nhanVien");

            BeanUtils.copyProperties(dto, input);
            input.setMaThietBi(null);
            input.setMaKhachHang(null);
            input.setCongTrinhs(null);
            // input.setMaNhanVien(null);
            createDataSheetExport(input, deleteFlag, workbook, sheetNew, sheetTemp, title);

            deleteSheet(workbook, "TMP_KHACH_HANG");
            deleteSheet(workbook, "TMP_NHAN_VIEN");
            deleteSheet(workbook, "TMP_CONG_TRINH");
            deleteSheet(workbook, "TMP_THIET_BI");
            deleteSheet(workbook, "TMP_TONG_HOP");
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tạo export với data get đc
     * @param dto
     * @param deleteFlag
     * @param workbook
     * @param sheetNew
     * @param sheetTemp
     * @return
     */
    private Workbook createDataSheetExport(RequestReportDto dto, Integer deleteFlag, Workbook workbook, Sheet sheetNew, Sheet sheetTemp,
        String title) {

        List<ReportDto> listDataExport = reportCustomRepository.findAll(dto, deleteFlag);

        Cell cellStyleCenter = sheetTemp.getRow(10).getCell(0);
        Cell cellStyleLeft = sheetTemp.getRow(10).getCell(2);
        Cell cellStyleRight = sheetTemp.getRow(10).getCell(7);

        ExcelUtils.copySheet(workbook, sheetTemp, sheetNew, 0, 11);

        int startRow = 10; // ví dụ: dữ liệu bắt đầu từ dòng 2 (index = 1)
        int sizeDate = listDataExport != null ? listDataExport.size() : 0;

        // xử lý tên;
        Row row4 = sheetNew.createRow(4);
        Cell cellStyleTitle = sheetTemp.getRow(4).getCell(3);
        Cell cellStyleFromTo = sheetTemp.getRow(6).getCell(3);
        ExcelUtils.copyCell(workbook, cellStyleTitle, row4.createCell(3));
        row4.getCell(3).setCellValue(title);
        Row row5 = sheetNew.createRow(5);
        ExcelUtils.copyCell(workbook, cellStyleFromTo, row5.createCell(3));
        row5.getCell(3).setCellValue("Từ Ngày: " + DateUtils.formatDate(dto.getNgayChungTuFrom(), DateUtils.DATE_DD_MM_YYYY));
        Row row6 = sheetNew.createRow(6);
        ExcelUtils.copyCell(workbook, cellStyleFromTo, row6.createCell(3));
        row6.getCell(3).setCellValue("Đến Ngày: " + DateUtils.formatDate(dto.getNgayChungTuTo(), DateUtils.DATE_DD_MM_YYYY));

        BigDecimal tongSoChuyen = BigDecimal.ZERO;
        BigDecimal tongSoKm = BigDecimal.ZERO;
        BigDecimal tongSoTan = BigDecimal.ZERO;
        BigDecimal tongKhoiLuong = BigDecimal.ZERO;
        BigDecimal tongSoGio = BigDecimal.ZERO;
        BigDecimal tongSoCa = BigDecimal.ZERO;
        BigDecimal tongTien = BigDecimal.ZERO;
        for (ReportDto data : listDataExport) {
            int cellIndexStyle = 0;
            Row row = sheetNew.createRow(startRow);
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(cellIndexStyle)); // vị trị cell 0
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleLeft, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleLeft, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle)); // vị trị cell 5
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleRight, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle)); // vị trị cell 10
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle)); // vị trị cell 15
            ExcelUtils.copyCell(workbook, cellStyleRight, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleRight, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleLeft, row.createCell(++cellIndexStyle));

            int cellIndexValue = 0;
            row.getCell(cellIndexValue).setCellValue(data.getNgayChungTuDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getSoPhieu());
            row.getCell(++cellIndexValue).setCellValue(data.getDienGiai());
            row.getCell(++cellIndexValue).setCellValue(data.getTenThietBi());
            row.getCell(++cellIndexValue).setCellValue(data.getSoChuyenDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getSoKmDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getSoTanDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getKhoiLuongDisp());

            row.getCell(++cellIndexValue).setCellValue(data.getCaSangStartDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getCaSangEndDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getCaChieuStartDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getCaChieuEndDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getCaToiStartDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getCaToiEndDisp());

            row.getCell(++cellIndexValue).setCellValue(data.getTongGioDisp());

            row.getCell(++cellIndexValue).setCellValue(data.getSoCaDisp());

            row.getCell(++cellIndexValue).setCellValue(data.getDonGiaDisp());
            row.getCell(++cellIndexValue).setCellValue(data.getThanhTienDisp());

            row.getCell(++cellIndexValue).setCellValue(data.getTenCongTrinh());

            tongSoChuyen = tongSoChuyen.add(data.getSoChuyen() != null ? data.getSoChuyen() : BigDecimal.ZERO);
            tongSoKm = tongSoKm.add(data.getSoKm() != null ? data.getSoKm() : BigDecimal.ZERO);
            tongSoTan = tongSoTan.add(data.getSoTan() != null ? data.getSoTan() : BigDecimal.ZERO);
            tongKhoiLuong = tongKhoiLuong.add(data.getKhoiLuong() != null ? data.getKhoiLuong() : BigDecimal.ZERO);

            tongSoGio = tongSoGio.add(StringUtils.hasText(data.getTongGioDisp()) ? new BigDecimal(data.getTongGioDisp()) : BigDecimal.ZERO);
            tongSoCa = tongSoCa.add(StringUtils.hasText(data.getTongGioDisp()) ? new BigDecimal(data.getSoCaDisp()) : BigDecimal.ZERO);
            tongTien = tongTien.add(StringUtils.hasText(data.getTongGioDisp()) ? new BigDecimal(data.getThanhTienDisp()) : BigDecimal.ZERO);

            ++startRow;
        }

        if (sizeDate != 0) {
            Row row = sheetNew.createRow(startRow);
            Cell cellStyle = sheetTemp.getRow(11).getCell(0);
            Cell cellOne = sheetTemp.getRow(11).getCell(1);
            int cellIndexStyle = 0;
            ExcelUtils.copyCell(workbook, cellStyle, row.createCell(cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellOne, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellOne, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleRight, row.createCell(++cellIndexStyle));
            row.getCell(4).setCellValue(FormatUtils.formatBigDecimal(tongSoChuyen, 2));
            row.getCell(5).setCellValue(FormatUtils.formatBigDecimal(tongSoKm, 2));
            row.getCell(6).setCellValue(FormatUtils.formatBigDecimal(tongSoTan, 2));
            row.getCell(7).setCellValue(FormatUtils.formatBigDecimal(tongKhoiLuong, 2));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleRight, row.createCell(++cellIndexStyle));
            ExcelUtils.copyCell(workbook, cellStyleCenter, row.createCell(++cellIndexStyle));
            row.getCell(14).setCellValue(FormatUtils.formatBigDecimal(tongSoGio, 2));
            row.getCell(15).setCellValue(FormatUtils.formatBigDecimal(tongSoCa, 2));
            row.getCell(17).setCellValue(FormatUtils.formatBigDecimal(tongTien, 2));
            sheetNew.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 3));
        }

        return workbook;

    }

    private void deleteSheet(Workbook workbook, String sheetName) {
        workbook.removeSheetAt(workbook.getSheetIndex(sheetName));

    }

    private String getTitle(RequestReportDto dto, String condition) {

        if ((!Objects.isNull(dto.getMaThietBi()) && Objects.equals("thietBi", condition))
            || Objects.equals("All", condition)) {
            List<ThietBi> dataList = thietBiRepository.findByMaThietBiAndDeleteFlag(dto.getMaThietBi(),
                CommonConstant.DELETE_FLAG_ENABLE);
            if (dataList != null && !dataList.isEmpty()) {
                return "Tên Thiết Bị: " + dataList.get(0).getTenThietBi();
            }

        }

        if ((!Objects.isNull(dto.getMaCongTrinh()) && Objects.equals("congTrinh", condition))
            || Objects.equals("All", condition)) {
            List<CongTrinh> dataList = congTrinhRepository.findByMaCongTrinhAndDeleteFlag(dto.getMaCongTrinh(),
                CommonConstant.DELETE_FLAG_ENABLE);
            if (dataList != null && !dataList.isEmpty()) {
                return "Tên Công Trình: " + dataList.get(0).getTenCongTrinh();
            }

        }

        if ((!Objects.isNull(dto.getMaKhachHang()) && Objects.equals("khachHang", condition))
            || Objects.equals("All", condition)) {
            List<KhachHang> dataList = khachHangRepository.findByMaKhachHangAndDeleteFlag(dto.getMaKhachHang(),
                CommonConstant.DELETE_FLAG_ENABLE);
            if (dataList != null && !dataList.isEmpty()) {
                return "Tên Khách Hàng: " + dataList.get(0).getTenKhachHang();
            }

        }

        if ((!Objects.isNull(dto.getMaNhanVien()) && Objects.equals("nhanVien", condition))
            || Objects.equals("All", condition)) {
            List<NhanVien> dataList = nhanVienRepository.findByMaNhanVienAndDeleteFlag(dto.getMaNhanVien(),
                CommonConstant.DELETE_FLAG_ENABLE);
            if (dataList != null && !dataList.isEmpty()) {
                return "Tên Nhân Viên: " + dataList.get(0).getTenNhanVien();
            }

        }

        return "Tên máy móc, thiết bị:";
    }
}
