package com.vn.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {
    /**
     * Copy toàn bộ 1 row (bao gồm style, value, công thức, merged cell).
     */
    public static void copyRow(Workbook workbook, Sheet sheet, int sourceRowNum, int targetRowNum) {
        Row sourceRow = sheet.getRow(sourceRowNum);
        Row newRow = sheet.getRow(targetRowNum);

        if (newRow != null) {
            sheet.shiftRows(targetRowNum, sheet.getLastRowNum(), 1);
        }
        newRow = sheet.createRow(targetRowNum);
        newRow.setHeight(sourceRow.getHeight());

        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            if (oldCell == null) continue;

            Cell newCell = newRow.createCell(i);
            copyCell(workbook, oldCell, newCell);
        }

        // Copy merge region (nếu có)
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(
                    newRow.getRowNum(),
                    newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
                    cellRangeAddress.getFirstColumn(),
                    cellRangeAddress.getLastColumn()
                );
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }


    }

    /**
     * Copy toàn bộ thông tin 1 ô (value, style, type, formula)
     */
    /*private static void copyCell(Workbook workbook, Cell oldCell, Cell newCell) {
        CellStyle newCellStyle = workbook.createCellStyle();
        newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
        newCell.setCellStyle(newCellStyle);

        switch (oldCell.getCellType()) {
            case STRING:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case BLANK:
                newCell.setBlank();
                break;
            default:
                break;
        }
    }*/

    /**
     * Copy toàn bộ sheet (bao gồm style, border, màu nền, công thức, merge cell)
     */
    public static void copySheet(Workbook workbook, Sheet sourceSheet, Sheet targetSheet, Integer startLine,  Integer endtLine) {
        // Copy từng row
        for (int i = startLine; i <= endtLine; i++) {
            Row srcRow = sourceSheet.getRow(i);
            if (srcRow == null) continue;

            Row destRow = targetSheet.createRow(i);
            destRow.setHeight(srcRow.getHeight());

            for (int j = srcRow.getFirstCellNum(); j < srcRow.getLastCellNum(); j++) {
                Cell oldCell = srcRow.getCell(j);
                if (oldCell == null) continue;

                Cell newCell = destRow.createCell(j);
                copyCell(workbook, oldCell, newCell);
            }
        }

        // Copy merge regions
        for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            CellRangeAddress region = sourceSheet.getMergedRegion(i);
            targetSheet.addMergedRegion(region.copy());
        }

        // Copy độ rộng cột
        for (int i = 0; i <= sourceSheet.getRow(0).getLastCellNum(); i++) {
            targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
        }
    }

    /**
     * Copy 1 ô (value, style, formula, v.v.)
     */
    public static void copyCell(Workbook workbook, Cell oldCell, Cell newCell) {
        CellStyle newCellStyle = workbook.createCellStyle();
        newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
        newCell.setCellStyle(newCellStyle);

        switch (oldCell.getCellType()) {
            case STRING -> newCell.setCellValue(oldCell.getStringCellValue());
            case NUMERIC -> newCell.setCellValue(oldCell.getNumericCellValue());
            case BOOLEAN -> newCell.setCellValue(oldCell.getBooleanCellValue());
            case FORMULA -> newCell.setCellFormula(oldCell.getCellFormula());
            case BLANK -> newCell.setBlank();
            default -> {}
        }
    }
}
