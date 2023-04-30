package com.example.asm.controller;

import com.example.asm.dto.CveDto;
import com.example.asm.service.ExportExcelCve;
import com.example.asm.service.ICveService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "chart")

public class ChartController {
    @Autowired
    ICveService service;

//    @GetMapping(value = "{id}")
//    public List<CveDto> findAllCveByDomainId(Model model,
//                                @PathVariable("id") int idDomain){
//        List<CveDto> cveDtos = service.findAllByDomainId(idDomain);
//        return cveDtos;
//    }

    @GetMapping("{id}")
    public String findAllCveByDomainId(Model model, @PathVariable("id") int idDomain){
        List<CveDto> cves = this.service.findAllByDomainId(idDomain);

        List<CveDto> cvesNoRick= this.service.findAllByDomainIdAndCvssPointIsNull(idDomain);
        List<CveDto> cvesLow = this.service.findAllByDomainIdAndCvssPointLow(idDomain);
        List<CveDto> cvesMedium = this.service.findAllByDomainIdAndCvssPointMedium(idDomain);
        List<CveDto> cvesHigh = this.service.findAllByDomainIdAndCvssPointHigh(idDomain);
        List<CveDto> cvesCritical = this.service.findAllByDomainIdAndCvssPointCritical(idDomain);

        model.addAttribute("cvesNoRick", cvesNoRick);
        model.addAttribute("cvesLow", cvesLow);
        model.addAttribute("cvesMedium", cvesMedium);
        model.addAttribute("cvesHigh", cvesHigh);
        model.addAttribute("cvesCritical", cvesCritical);
        model.addAttribute("cves", cves);
        return "chart";
    }


    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response, Pageable pageable) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<CveDto> cveDtos = service.findAll();

        ExportExcelCve excelExporter = new ExportExcelCve(cveDtos);

        excelExporter.export(response);
    }
}
