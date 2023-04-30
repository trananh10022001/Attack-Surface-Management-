package com.example.asm.service;

import com.example.asm.dto.DomainDto;
import com.example.asm.dto.ResultHttpxDto;
import com.example.asm.dto.SubdomainDto;
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
import java.util.List;

public class ExportExcelDomain {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private int rowCount;
    private DomainDto domainDto;
    private List<SubdomainDto> subdomainDtoList;
    private List<ResultHttpxDto> resultHttpxDtoList;

    public ExportExcelDomain(DomainDto domainDto, List<SubdomainDto> subdomainDtoList, List<ResultHttpxDto> resultHttpxDtoList) {
        this.domainDto = domainDto;
        this.subdomainDtoList = subdomainDtoList;
        this.resultHttpxDtoList = resultHttpxDtoList;
        workbook = new XSSFWorkbook();
        rowCount = 0;
    }

    private void writeDomainHeaderLine() {
        sheet = workbook.createSheet("Domain");

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Row row = sheet.createRow(rowCount);
        int columnCount = 0;

        createCell(row, columnCount++, "Domain ID", style);
        createCell(row, columnCount++, "Domain Name", style);
        createCell(row, columnCount, "Create Date", style);
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

    private void writeDomainDataLines() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        Row row = sheet.createRow(++rowCount);
        int columnCount = 0;

        createCell(row, columnCount++, domainDto.getId(), style);
        createCell(row, columnCount++, domainDto.getDomainName(), style);
        createCell(row, columnCount, domainDto.getCreatedDate().toString(), style);
    }

    private void writeSubdomainHeaderLine() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Row row = sheet.createRow(++rowCount);
        int columnCount = 0;

        createCell(row, columnCount++, "Subdomain ID", style);
        createCell(row, columnCount++, "Subdomain Name", style);
        createCell(row, columnCount, "Create Date", style);
    }

    private void writeSubdomainDataLine() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        if (!this.subdomainDtoList.isEmpty()) {
            for (SubdomainDto subdomainDto : subdomainDtoList) {
                Row row = sheet.createRow(++rowCount);
                int columnCount = 0;

                createCell(row, columnCount++, subdomainDto.getId(), style);
                createCell(row, columnCount++, subdomainDto.getSubdomainName(), style);
                createCell(row, columnCount, subdomainDto.getCreatedDate().toString(), style);
            }
        } else {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;

            createCell(row, columnCount++, "No Information", style);
            createCell(row, columnCount++, "No Information", style);
            createCell(row, columnCount, "No Information", style);
        }
    }

    private void writeResultHttpxHeaderLine() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Row row = sheet.createRow(++rowCount);
        int columnCount = 0;

        createCell(row, columnCount++, "ResultHttpx ID", style);
        createCell(row, columnCount, "Output", style);
    }

    private void writeResultHttpxDataLine() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        if (!this.resultHttpxDtoList.isEmpty()) {
            for (ResultHttpxDto resultHttpxDto : resultHttpxDtoList) {
                Row row = sheet.createRow(++rowCount);
                int columnCount = 0;

                createCell(row, columnCount++, resultHttpxDto.getId(), style);
                createCell(row, columnCount, resultHttpxDto.getOutput(), style);
            }
        } else {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;

            createCell(row, columnCount++, "No Information", style);
            createCell(row, columnCount, "No Information", style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeDomainHeaderLine();
        writeDomainDataLines();
        writeSubdomainHeaderLine();
        writeSubdomainDataLine();
        writeResultHttpxHeaderLine();
        writeResultHttpxDataLine();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
