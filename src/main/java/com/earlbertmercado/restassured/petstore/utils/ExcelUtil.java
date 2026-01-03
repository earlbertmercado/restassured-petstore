package com.earlbertmercado.restassured.petstore.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    public static List<Map<String, String>> getSheetData(String path, String sheetName) {
        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            return parseSheet(sheet);

        } catch (IOException e) {
            throw new RuntimeException("Failed to access Excel file: " + e.getMessage());
        }
    }

    private static List<Map<String, String>> parseSheet(Sheet sheet) {
        List<Map<String, String>> data = new ArrayList<>();
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) return data;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow != null) {
                data.add(mapRowToData(headerRow, currentRow));
            }
        }
        return data;
    }

    private static Map<String, String> mapRowToData(Row headerRow, Row currentRow) {
        Map<String, String> rowData = new LinkedHashMap<>();

        for (int j = 0; j < headerRow.getLastCellNum(); j++) {
            String columnName = getCellValue(headerRow.getCell(j));
            String cellValue = getCellValue(currentRow.getCell(j));
            rowData.put(columnName, cellValue);
        }
        return rowData;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }
}
