package com.example.asm.controller;

import com.example.asm.dto.SubdomainDto;
import com.example.asm.service.ISubdomainService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/subdomain")
public class SubdomainController {
    @Autowired
    ISubdomainService service;
    @GetMapping
    public String findAllResultNuclei(Model model,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size,
                                      @RequestParam("search") Optional<String> search,
                                      @RequestParam("id") Optional<Integer> id,
                                      HttpServletRequest request){
        int pageIndex = page.orElse(1);
        int pageSize = size.orElse(5);
        String searchField = search.orElse(null);
        Page<SubdomainDto> resultPage = this.service.findAllPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()));
        if(searchField!=null){
            searchField = URLDecoder.decode(searchField);
            resultPage = this.service.searchPage(PageRequest.of(pageIndex-1,pageSize,Sort.by("id").descending()),searchField);
        }
        if(id.isPresent()){
            resultPage = this.service.searchPageByDomainId(PageRequest.of(pageIndex-1,pageSize,Sort.by("id").descending()),id.get());
        }
        List<Integer> result = this.service.checkDifferentBetweenScan();
        model.addAttribute("diffSubdomain", result);
        model.addAttribute("result", resultPage);
        int totalPage = resultPage.getTotalPages();
        if(totalPage>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("curPage", pageIndex);
        }
        request.getSession().setAttribute("ref",request.getRequestURI());
        model.addAttribute("searchField", searchField);
        return "subdomain";
    }

    @GetMapping("/more-information")
    public String getMoreInformation(Model model, @RequestParam("id") Integer id){
        Map<String, Object> result = service.getMoreInformation(id);
        model.addAttribute("infor", result);
        return "subdomain";
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, @RequestParam("id") Integer id){
        service.export(id, response);
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") Integer id){
        service.deleteById(id);
        return "redirect:/subdomain";
    }
}
