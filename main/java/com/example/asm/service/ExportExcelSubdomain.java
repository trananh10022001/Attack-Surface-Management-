package com.example.asm.service;

import com.example.asm.dto.DomainDto;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.Map;

public class ExportExcelSubdomain {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Map<String, Object> subdomain;

    public ExportExcelSubdomain(Map<String, Object> subdomain) {
        this.subdomain = subdomain;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Subdomain");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Subdomain", style);
        createCell(row, 1, "IP", style);
        createCell(row, 2, "Port", style);
        createCell(row, 3, "Technical", style);
        createCell(row, 4, "Vul Scan", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
        sheet.autoSizeColumn(columnCount);
    }

    private void writeDataLines() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        Row row = sheet.createRow(1);
        int columnCount = 0;

        createCell(row, columnCount++, subdomain.get("subdomain"), style);
        createCell(row, columnCount++, subdomain.get("ip"), style);
        createCell(row, columnCount++, subdomain.get("port").toString().replace("[", "").replace("]", ""), style);
        createCell(row, columnCount++, subdomain.get("technical").toString().replace("[", "").replace("]", ""), style);
        createCell(row, columnCount, subdomain.get("vul").toString().replace("[", "").replace("]", ""), style);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
