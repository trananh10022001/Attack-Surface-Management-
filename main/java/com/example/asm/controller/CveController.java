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
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping(value = "/cve")
public class CveController {
    @Autowired
    ICveService service;
    @GetMapping
    public String findAllCve(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam("search") Optional<String> search,
                                HttpServletRequest request){
        int pageIndex = page.orElse(1);
        int pageSize = size.orElse(5);
        String searchField = search.orElse(null);
        Page<CveDto> cvePage = this.service.findAllPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()));
        if(searchField!=null){
            searchField = URLDecoder.decode(searchField);
            cvePage = this.service.searchPage(PageRequest.of(pageIndex-1,pageSize,Sort.by("id").descending()),searchField);
        }
        model.addAttribute("cves", cvePage);
        int totalPage = cvePage.getTotalPages();
        if(totalPage>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("curPage", pageIndex);
        }
        request.getSession().setAttribute("ref",request.getRequestURI());
        model.addAttribute("searchField", searchField);
        return "cve";
    }

    @GetMapping(value = "/all")
    public String findCveAll(Model model,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam("search") Optional<String> search,
                             HttpServletRequest request){
        int pageIndex = page.orElse(1);
        int pageSize = size.orElse(5);
        String searchField = search.orElse(null);
        Page<CveDto> cvePage = this.service.findAllPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()));
        if(searchField!=null){
            searchField = URLDecoder.decode(searchField);
            cvePage = this.service.searchPage(PageRequest.of(pageIndex-1,pageSize,Sort.by("id").descending()),searchField);
        }
        model.addAttribute("cves", cvePage);
        int totalPage = cvePage.getTotalPages();
        if(totalPage>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("curPage", pageIndex);
        }
        request.getSession().setAttribute("ref",request.getRequestURI());
        model.addAttribute("searchField", searchField);
        return "cveAll";
    }

    @GetMapping("{id}")
    public String findAllCveByDomainId(Model model,
                             @PathVariable("id") int id,
                             @RequestParam("chartColumn") Integer chartColumn,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam("search") Optional<String> search,
                             HttpServletRequest request){
        int pageIndex = page.orElse(1);
        int pageSize = size.orElse(5);
        String searchField = search.orElse(null);

        Page<CveDto> cvePage = null;
        if(chartColumn == 0){
            cvePage = this.service.findAllByDomainIdAndCvssPointCriticalPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id);
        } else if(chartColumn == 1){
            cvePage = this.service.findAllByDomainIdAndCvssPointHighPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id);
        } else if(chartColumn == 2){
            cvePage = this.service.findAllByDomainIdAndCvssPointMediumPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id);
        } else if(chartColumn == 3){
            cvePage = this.service.findAllByDomainIdAndCvssPointLowPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id);
        } else if(chartColumn == 4){
            cvePage = this.service.findAllByDomainIdAndCvssPointIsNullPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id);
        }

        if(searchField!=null){
            searchField = URLDecoder.decode(searchField);
            if(chartColumn == 0){
                cvePage = this.service.searchAllByDomainIdAndCvssPointCriticalPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id, searchField);
            } else if(chartColumn == 1){
                cvePage = this.service.searchAllByDomainIdAndCvssPointHighPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id,searchField);
            } else if(chartColumn == 2){
                cvePage = this.service.searchAllByDomainIdAndCvssPointMediumPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id,searchField);
            } else if(chartColumn == 3){
                cvePage = this.service.searchAllByDomainIdAndCvssPointLowPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id,searchField);
            } else if(chartColumn == 4){
                cvePage = this.service.searchAllByDomainIdAndCvssPointIsNullPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()), id,searchField);
            }
        }
        model.addAttribute("cves", cvePage);
        int totalPage = cvePage.getTotalPages();
        if(totalPage>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("curPage", pageIndex);
        }
        request.getSession().setAttribute("ref",request.getRequestURI());
        model.addAttribute("searchField", searchField);
        return "cve";
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
