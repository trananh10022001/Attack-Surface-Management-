package com.example.asm.controller;

import com.example.asm.dto.DomainDto;
import com.example.asm.entity.DomainEntity;
import com.example.asm.service.IDomainService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ScanController {
    @Autowired
    IDomainService domainService;
    @GetMapping(value = "/scan")
    public String findAllDomain(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam("search") Optional<String> search,
                                HttpServletRequest request){
        int pageIndex = page.orElse(1);
        int pageSize = size.orElse(5);
        String searchField = search.orElse(null);
        Page<DomainDto> domainPage = this.domainService.findAllPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("createdDate").descending()));
        if(searchField!=null){
            searchField = URLDecoder.decode(searchField);
            domainPage = this.domainService.searchPage(PageRequest.of(pageIndex-1,pageSize,Sort.by("createdDate").descending()),searchField);
        }
        model.addAttribute("domains", domainPage);
        int totalPage = domainPage.getTotalPages();
        if(totalPage>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("curPage", pageIndex);
        }
        request.getSession().setAttribute("ref",request.getRequestURI());
        model.addAttribute("searchField", searchField);
        return "allscan";
    }
    @GetMapping(value = "scan-settings")
    public String getSetting(){
        return "setting";
    }

    @GetMapping(value = "scan-edit")
    public String showEditForm(@RequestParam("id") int id, Model model){
        model.addAttribute("domain", this.domainService.findById(id));
        return "edit-scan";
    }
    @PostMapping(value = "edit-scan")
    public String showEditForm(@ModelAttribute DomainEntity domain){
        this.domainService.updateDomain(domain);
        return "redirect:/scan";
    }
    @GetMapping(value = "scan-delete")
    public String deleteScan(@RequestParam("id") int id){
        this.domainService.deleteDomain(id);
        return "redirect:/scan";
    }
}
