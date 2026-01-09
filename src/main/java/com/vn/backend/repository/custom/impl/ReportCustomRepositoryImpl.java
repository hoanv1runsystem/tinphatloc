package com.vn.backend.repository.custom.impl;

import com.vn.backend.dto.ExportChiPhiDto;
import com.vn.backend.dto.ExportNhatKyXeDto;
import com.vn.backend.dto.RequestExportDto;
import com.vn.backend.repository.custom.ReportCustomRepository;
import com.vn.utils.DateUtils;
import com.vn.utils.FormatUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReportCustomRepositoryImpl implements ReportCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ExportNhatKyXeDto> findAll(RequestExportDto request, Integer deleteFlag) {
        StringBuilder sql = new StringBuilder("");

        sql.append(" select ");
        sql.append("                nkx.id,");
        sql.append("                nkx.ngay_chung_tu,");
        sql.append("                nkx.so_phieu,");
        sql.append("                nkx.mo_ta,");
        sql.append("                nkx.so_chuyen ,");
        sql.append("                nkx.so_km,");
        sql.append("                nkx.so_tan,");
        sql.append("                nkx.ca_sang_start,");
        sql.append("                nkx.ca_sang_end,");
        sql.append("                nkx.ca_chieu_start,");
        sql.append("                nkx.ca_chieu_end,");
        sql.append("                nkx.ca_toi_start,");
        sql.append("                nkx.ca_toi_end,");
        sql.append("                nkx.khoi_luong,");
        sql.append("                nkx.don_gia,");
        sql.append("                nkx.vat,");
        sql.append("                nkx.ma_dvt,");

        sql.append("                nkx.ma_cong_trinh,");
        sql.append("                ct.ten_cong_trinh,");

        sql.append("                nkx.ma_nhan_vien,");
        sql.append("                nv.ten_nhan_vien,");

        sql.append("                nkx.ma_khach_hang,");
        sql.append("                kh.ten_khach_hang,");

        sql.append("                nkx.ma_thiet_bi,");
        sql.append("                tb.ten_thiet_bi,");
        sql.append("                tb.dvt,");
        sql.append("                tb.dvt_time");

        sql.append(" from tbl_nhat_ky_xe nkx ");
        sql.append(" left join tbl_cong_trinh ct ");
        sql.append("    on nkx.ma_cong_trinh = ct.ma_cong_trinh ");
        sql.append("    and ct.delete_flag = 0 ");
        sql.append(" left join tbl_nhan_vien nv  ");
        sql.append("    on nkx.ma_nhan_vien = nv.ma_nhan_vien ");
        sql.append("    and nv.delete_flag = 0 ");
        sql.append(" left join tbl_khach_hang kh ");
        sql.append("    on nkx.ma_khach_hang = kh.ma_khach_hang ");
        sql.append(" and kh.delete_flag = 0 ");
        sql.append(" left join tbl_thiet_bi tb ");
        sql.append("    on nkx.ma_thiet_bi = tb.ma_thiet_bi ");
        sql.append(" and tb.delete_flag = 0 ");

        sql.append(" where nkx.delete_flag = 0 ");

        if (StringUtils.hasText(request.getMaThietBi())) {
            sql.append(" and  nkx.ma_thiet_bi = :maThietBi");
        }
        if (StringUtils.hasText(request.getMaKhachHang())) {
            sql.append(" and  nkx.ma_khach_hang = :maKhachHang");
        }

        if (StringUtils.hasText(request.getMaCongTrinh())) {
            sql.append(" and  nkx.ma_cong_trinh = :maCongTrinh");
        }

        if (!ObjectUtils.isEmpty(request.getNgayChungTuFrom()) && !ObjectUtils.isEmpty(request.getNgayChungTuTo())) {
            sql.append(" and  nkx.ngay_chung_tu between :ngayChungTuFrom and :ngayChungTuTo");
        }

        sql.append(" order by ");
        sql.append("                nkx.ngay_chung_tu, ");
        sql.append("                so_phieu, ");
        sql.append("                tb.ten_thiet_bi, ");
        sql.append("                ct.ten_cong_trinh, ");
        sql.append("                kh.ten_khach_hang, ");
        sql.append("                nv.ten_nhan_vien");

        Query query = entityManager.createNativeQuery(sql.toString());
        //query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(request.getMaThietBi())) {
            query.setParameter("maThietBi", request.getMaThietBi());
        }
        if (StringUtils.hasText(request.getMaKhachHang())) {
            query.setParameter("maKhachHang", request.getMaKhachHang());
        }

        if (StringUtils.hasText(request.getMaCongTrinh())) {
            query.setParameter("maCongTrinh", request.getMaCongTrinh());
        }

        if (!ObjectUtils.isEmpty(request.getNgayChungTuFrom()) && !ObjectUtils.isEmpty(request.getNgayChungTuTo())) {
            query.setParameter("ngayChungTuFrom", request.getNgayChungTuFrom());
            query.setParameter("ngayChungTuTo", request.getNgayChungTuTo());
        }

        List<Object[]> rows = query.getResultList();
        List<ExportNhatKyXeDto> result = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            ExportNhatKyXeDto dto = new ExportNhatKyXeDto();
            dto.setId(((Number) row[i]).longValue());
            dto.setNgayChungTu(row[++i] != null ? ((java.sql.Date) row[i]).toLocalDate() : null);
            dto.setSoPhieu(row[++i] != null ? (String) row[i] : "");
            dto.setDienGiai(row[++i] != null ? (String) row[i] : "");
            dto.setSoChuyen(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);
            dto.setSoKm(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);
            dto.setSoTan(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);

            dto.setCaSangStart(convertDatetime(row[++i]));
            dto.setCaSangEnd(convertDatetime(row[++i]));

            dto.setCaChieuStart(convertDatetime(row[++i]));
            dto.setCaChieuEnd(convertDatetime(row[++i]));

            dto.setCaToiStart(convertDatetime(row[++i]));
            dto.setCaToiEnd(convertDatetime(row[++i]));

            dto.setKhoiLuong(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);
            dto.setDonGia(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);
            dto.setVat(row[++i] != null ? ((Number) row[i]).intValue() : 0);
            dto.setMaDvt(row[++i] != null ? (String) row[i] : null);

            dto.setMaCongTrinh(row[++i] != null ? (String) row[i] : "");
            dto.setTenCongTrinh(row[++i] != null ? (String) row[i] : "");

            dto.setMaNhanVien(row[++i] != null ? (String) row[i] : "");
            dto.setTenNhanVien(row[++i] != null ? (String) row[i] : "");

            dto.setMaKhachHang(row[++i] != null ? (String) row[i] : "");
            dto.setTenKhachHang(row[++i] != null ? (String) row[i] : "");

            dto.setMaThietBi(row[++i] != null ? (String) row[i] : "");
            dto.setTenThietBi(row[++i] != null ? (String) row[i] : "");
            dto.setDvt(row[++i] != null ? (String) row[i] : "");
            dto.setDvtTime(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);

            dto.setNgayChungTuDisp(DateUtils.formatDate(dto.getNgayChungTu(), DateUtils.DATE_DD_MM_YYYY));
            dto.setSoTanDisp(FormatUtils.formatBigDecimal(dto.getSoTan(), 2));
            dto.setSoChuyenDisp(FormatUtils.formatBigDecimal(dto.getSoChuyen(), 2));
            dto.setSoKmDisp(FormatUtils.formatBigDecimal(dto.getSoKm(), 2));

            dto.setCaSangStartDisp(FormatUtils.formatTimehhmm(dto.getCaSangStart()));
            dto.setCaSangEndDisp(FormatUtils.formatTimehhmm(dto.getCaSangEnd()));

            dto.setCaChieuStartDisp(FormatUtils.formatTimehhmm(dto.getCaChieuStart()));
            dto.setCaChieuEndDisp(FormatUtils.formatTimehhmm(dto.getCaChieuEnd()));

            dto.setCaToiStartDisp(FormatUtils.formatTimehhmm(dto.getCaToiStart()));
            dto.setCaToiEndDisp(FormatUtils.formatTimehhmm(dto.getCaToiEnd()));

            BigDecimal soGioCaSang = FormatUtils.soGioCa(dto.getCaSangStart(), dto.getCaSangEnd());
            BigDecimal soGioCaChieu = FormatUtils.soGioCa(dto.getCaChieuStart(), dto.getCaChieuEnd());
            BigDecimal soGioCaToi = FormatUtils.soGioCa(dto.getCaToiStart(), dto.getCaToiEnd());

            BigDecimal tongGio = soGioCaSang.add(soGioCaSang).add(soGioCaChieu).add(soGioCaToi).setScale(1, RoundingMode.DOWN);

            dto.setTongGio(tongGio);
            dto.setTongGioDisp(FormatUtils.formatBigDecimal(tongGio, 2));
            BigDecimal soCa = tongGio.divide(dto.getDvtTime(), 2, RoundingMode.HALF_UP);
            dto.setSoCa(soCa);
            dto.setSoCaDisp(FormatUtils.formatBigDecimal(soCa, 2));
            dto.setKhoiLuongDisp(FormatUtils.formatBigDecimal(dto.getKhoiLuong(), 2));
            dto.setDonGiaDisp(FormatUtils.formatBigDecimal(dto.getDonGia(), 2));
            dto.setDvtTimeDisp(FormatUtils.formatBigDecimal(dto.getDvtTime(), 2));

            BigDecimal thanhTien = BigDecimal.ZERO;
            if (Objects.equals(dto.getMaDvt(), "CA")) {
                thanhTien = dto.getDonGia() == null ? BigDecimal.ZERO : dto.getDonGia().multiply(soCa);
            } else {
                BigDecimal giaChuaVat = dto.getDonGia().multiply(dto.getKhoiLuong());
                BigDecimal vat = giaChuaVat.multiply(BigDecimal.valueOf(dto.getVat()).divide(BigDecimal.valueOf(100)));
                thanhTien = giaChuaVat.add(vat);
            }

            dto.setThanhTien(thanhTien);
            dto.setThanhTienDisp(FormatUtils.formatBigDecimal(thanhTien, 2));
            result.add(dto);

        }

        return result;
    }

    @Override
    public List<ExportChiPhiDto> exportChiPhi(RequestExportDto request, Integer deleteFlag) {
        StringBuilder sql = new StringBuilder("");

        sql.append(" select ");
        sql.append("                cp.id,");
        sql.append("                cp.ngay_chung_tu,");
        sql.append("                cp.so_phieu,");
        sql.append("                cp.mo_ta,");
        sql.append("                cp.ma_hang ,");
        sql.append("                cp.ten_hang,");
        sql.append("                cp.dvt,");
        sql.append("                cp.so_luong,");
        sql.append("                cp.don_gia,");
        sql.append("                cp.ma_ncc,");
        sql.append("                cp.ten_ncc,");
        sql.append("                cp.note,");
        sql.append("                cp.ma_thiet_bi,");
        sql.append("                tb.ten_thiet_bi,");
        sql.append("                tb.dvt as dvt_thiet_bi,");
        sql.append("                tb.dvt_time,");

        sql.append("                cp.ma_cong_trinh,");
        sql.append("                ct.ten_cong_trinh,");

        sql.append("                cp.ma_nhan_vien,");
        sql.append("                nv.ten_nhan_vien");

        sql.append(" from tbl_chi_phi cp ");
        sql.append(" left join tbl_thiet_bi tb ");

        sql.append("    on cp.ma_thiet_bi = tb.ma_thiet_bi ");
        sql.append(" and tb.delete_flag = 0 ");

        sql.append(" left join tbl_nhan_vien nv  ");
        sql.append("    on cp.ma_nhan_vien = nv.ma_nhan_vien ");
        sql.append("    and nv.delete_flag = 0 ");

        sql.append(" left join tbl_cong_trinh ct ");
        sql.append("    on cp.ma_cong_trinh = ct.ma_cong_trinh ");
        sql.append("    and ct.delete_flag = 0 ");

        sql.append(" where cp.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(request.getMaThietBi())) {
            sql.append(" and  cp.ma_thiet_bi = :maThietBi");
        }

        if (StringUtils.hasText(request.getMaCongTrinh())) {
            sql.append(" and  cp.ma_cong_trinh = :maCongTrinh");
        }

        if (!ObjectUtils.isEmpty(request.getNgayChungTuFrom()) && !ObjectUtils.isEmpty(request.getNgayChungTuTo())) {
            sql.append(" and  cp.ngay_chung_tu between :ngayChungTuFrom and :ngayChungTuTo");
        }

        sql.append(" order by ");
        sql.append("                cp.ngay_chung_tu, ");
        sql.append("                cp.so_phieu, ");
        sql.append("                tb.ten_thiet_bi, ");
        sql.append("                ct.ten_cong_trinh, ");
        sql.append("                nv.ten_nhan_vien");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(request.getMaThietBi())) {
            query.setParameter("maThietBi", request.getMaThietBi());
        }

        if (StringUtils.hasText(request.getMaCongTrinh())) {
            query.setParameter("maCongTrinh", request.getMaCongTrinh());
        }

        if (!ObjectUtils.isEmpty(request.getNgayChungTuFrom()) && !ObjectUtils.isEmpty(request.getNgayChungTuTo())) {
            query.setParameter("ngayChungTuFrom", request.getNgayChungTuFrom());
            query.setParameter("ngayChungTuTo", request.getNgayChungTuTo());
        }

        List<Object[]> rows = query.getResultList();
        List<ExportChiPhiDto> result = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            ExportChiPhiDto dto = new ExportChiPhiDto();
            dto.setId(((Number) row[i]).longValue());
            dto.setNgayChungTu(row[++i] != null ? ((java.sql.Date) row[i]).toLocalDate() : null);
            dto.setSoPhieu(row[++i] != null ? (String) row[i] : "");
            dto.setDienGiai(row[++i] != null ? (String) row[i] : "");

            dto.setMaHang(row[++i] != null ? (String) row[i] : "");
            dto.setTenHang(row[++i] != null ? (String) row[i] : "");
            dto.setDvt(row[++i] != null ? (String) row[i] : "");
            dto.setSoLuong(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);
            dto.setDonGia(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);
            dto.setMaNcc(row[++i] != null ? (String) row[i] : "");
            dto.setTenNcc(row[++i] != null ? (String) row[i] : "");
            dto.setNote(row[++i] != null ? (String) row[i] : "");

            dto.setMaThietBi(row[++i] != null ? (String) row[i] : "");
            dto.setTenThietBi(row[++i] != null ? (String) row[i] : "");
            dto.setDvtThietBi(row[++i] != null ? (String) row[i] : "");
            dto.setDvtTime(row[++i] != null ? (BigDecimal) row[i] : BigDecimal.ZERO);

            dto.setMaCongTrinh(row[++i] != null ? (String) row[i] : "");
            dto.setTenCongTrinh(row[++i] != null ? (String) row[i] : "");

            dto.setMaNhanVien(row[++i] != null ? (String) row[i] : "");
            dto.setTenNhanVien(row[++i] != null ? (String) row[i] : "");

            dto.setThanhTien(dto.getSoLuong().multiply(dto.getDonGia()));

            dto.setNgayChungTuDisp(DateUtils.formatDate(dto.getNgayChungTu(), DateUtils.DATE_DD_MM_YYYY));

            if (!ObjectUtils.isEmpty(dto.getThanhTien())) {
                dto.setThanhTienDisp(FormatUtils.formatBigDecimal(dto.getThanhTien(), 2));
            }
            if (!ObjectUtils.isEmpty(dto.getSoLuong())) {
                dto.setSoLuongDisp(FormatUtils.formatBigDecimal(dto.getSoLuong(), 2));
            }

            if (!ObjectUtils.isEmpty(dto.getDonGia())) {
                dto.setDonGiaDisp(FormatUtils.formatBigDecimal(dto.getDonGia(), 2));
            }
            if (!ObjectUtils.isEmpty(dto.getDvtTime())) {
                dto.setDvtTimeDisp(FormatUtils.formatBigDecimal(dto.getDvtTime(), 2));
            }

            result.add(dto);

        }

        return result;
    }

    private LocalDateTime convertDatetime(Object value) {
        if (value instanceof java.sql.Timestamp ts) {
            return ts.toLocalDateTime();
        } else {
            return null;
        }
    }

    /*@Override
    public Page<CongTrinhDto> findAllCongTrinh(String keyword, int deleteFlag, Pageable pageable) {
        StringBuilder sql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");

        StringBuilder tableJoin = new StringBuilder("");
        StringBuilder condition = new StringBuilder("");

        sql.append("SELECT ");
        sql.append("                ct.id, ");
        sql.append("                ct.ma_cong_trinh AS maCongTrinh, ");
        sql.append("                ct.ten_cong_trinh AS tenCongTrinh, ");
        sql.append("                ct.ma_khach_hang AS maKhachHang, ");
        sql.append("                ct.ma_tk AS maTk, ");
        sql.append("                ct.loai_cong_trinh AS loaiCongTrinh, ");
        sql.append("                ct.tinh_trang AS tinhTrang, ");
        sql.append("                ct.ngay_bat_dau AS ngayBatDau, ");
        sql.append("                ct.ngay_ket_thuc AS ngayKetThuc, ");
        sql.append("                ct.han_thanh_toan AS hanThanhToan, ");
        sql.append("                ct.du_toan AS duToan, ");
        sql.append("                ct.dia_chi AS diaChi, ");
        sql.append("                ct.ma_chu_dau_tu AS maChudauTu, ");
        sql.append("                ct.note AS note, ");
        sql.append("                ct.delete_flag AS deleteFlag, ");
        sql.append("                kh.ten_khach_hang AS tenKhachHang, ");
        sql.append("                cdt.ten_chu_dau_tu AS tenChuDauTu ");

        // Table
        tableJoin.append("            FROM tbl_cong_trinh ct");
        tableJoin.append("            LEFT JOIN tbl_khach_hang kh");
        tableJoin.append("             ON ct.ma_khach_hang = kh.ma_khach_hang ");
        tableJoin.append("            LEFT JOIN tbl_chu_dau_tu cdt");
        tableJoin.append("             ON ct.ma_chu_dau_tu = cdt.ma_chu_dau_tu ");

        // condition
        condition.append("             WHERE 1=1 AND ct.delete_flag = :deleteFlag ");

        if (StringUtils.hasText(keyword)) {
            condition.append(
                " AND (UPPER(ct.ma_cong_trinh) LIKE CONCAT('%', :keyword, '%') OR UPPER(ct.ten_cong_trinh) LIKE CONCAT('%', :keyword, '%')) ");
        }
        sql.append(tableJoin.toString());
        sql.append(condition.toString());

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("deleteFlag", deleteFlag);
        if (StringUtils.hasText(keyword)) {
            query.setParameter("keyword", keyword.toUpperCase());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> rows = query.getResultList();
        List<CongTrinhDto> content = new ArrayList<>();
        for (Object[] row : rows) {
            int i = 0;
            CongTrinhDto dto = new CongTrinhDto();
            dto.setId(((Number) row[0]).longValue());
            dto.setMaCongTrinh((String) row[1]);
            dto.setTenCongTrinh((String) row[2]);
            dto.setMaKhachHang((String) row[3]);
            dto.setMaTk((String) row[4]);
            dto.setLoaiCongTrinh((String) row[5]);
            dto.setTinhTrang(row[i++] != null ? (Integer) row[6] : null);

            dto.setNgayBatDau(row[7] != null ? ((java.sql.Date) row[7]).toLocalDate() : null);
            dto.setNgayKetThuc(row[8] != null ? ((java.sql.Date) row[8]).toLocalDate() : null);
            dto.setHanThanhToan(row[9] != null ? ((java.sql.Date) row[9]).toLocalDate() : null);
            dto.setDuToan(row[10] != null ? (BigDecimal) row[10] : null);
            dto.setDiaChi(row[11] != null ? (String) row[11] : null);
            dto.setMaChudauTu((String) row[12]);
            dto.setNote(row[13] != null ? (String) row[13] : null);
            dto.setDeleteFlag(row[14] != null ? (Integer) row[14] : null);
            dto.setTenKhachHang(row[15] != null ? (String) row[15] : null);
            dto.setTenChuDauTu(row[16] != null ? (String) row[16] : null);

            content.add(dto);
        }

        countSql.append("SELECT COUNT(*)");
        countSql.append(tableJoin.toString());
        countSql.append(condition.toString());

        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        countQuery.setParameter("deleteFlag", CommonConstant.DELETE_FLAG_ENABLE);
        if (StringUtils.hasText(keyword)) {
            countQuery.setParameter("keyword", keyword.toUpperCase());
        }
        long total = ((Number) countQuery.getSingleResult()).longValue();
        return new PageImpl<>(content, pageable, total);
    }*/
}
