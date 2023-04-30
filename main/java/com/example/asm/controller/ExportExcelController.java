package com.example.asm.controller;

import com.example.asm.dto.CveDto;
import com.example.asm.service.ExportExcelCve;
import com.example.asm.service.ICveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping(value = "/export/excel")
@RestController
public class ExportExcelController {
    @Autowired
    ICveService service;

    // export excel all by id domain and vss_point
    @GetMapping("/cve/{id}")
    public void exportToExcel(HttpServletResponse response,
                              @PathVariable("id") int id,
                              @RequestParam("chartColumn") Integer chartColumn) throws IOException {

        List<CveDto> cveList = null;
        if(chartColumn == 0){
            cveList = this.service.findAllByDomainIdAndCvssPointCritical(id);
        } else if(chartColumn == 1){
            cveList = this.service.findAllByDomainIdAndCvssPointHigh(id);
        } else if(chartColumn == 2){
            cveList = this.service.findAllByDomainIdAndCvssPointMedium(id);
        } else if(chartColumn == 3){
            cveList = this.service.findAllByDomainIdAndCvssPointLow(id);
        } else if(chartColumn == 4){
            cveList = this.service.findAllByDomainIdAndCvssPointIsNull(id);
        }

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExportExcelCve excelExporter = new ExportExcelCve(cveList);

        excelExporter.export(response);
    }

    // export excel all by id domain
    @GetMapping("/chart/{id}")
    public void exportToExcelAllCve(HttpServletResponse response, @PathVariable("id") int id) throws IOException {

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        List<CveDto> cveList = this.service.findAllByDomainId(id);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cves_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExportExcelCve excelExporter = new ExportExcelCve(cveList);

        excelExporter.export(response);
    }
}
