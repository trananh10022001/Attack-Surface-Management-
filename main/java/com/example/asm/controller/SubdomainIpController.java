package com.example.asm.controller;

import com.example.asm.dto.SubdomainDto;
import com.example.asm.dto.SubdomainIpDto;
import com.example.asm.mapper.SubdomainIpMapper;
import com.example.asm.mapper.SubdomainMapper;
import com.example.asm.repository.ISubdomainIpRepository;
import com.example.asm.repository.ISubdomainRepository;
import com.example.asm.service.ISubdomainIpService;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/subdomain-ip")
public class SubdomainIpController {
    @Autowired
    ISubdomainIpService service;
    @GetMapping
    public String findAllResultNuclei(Model model,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size,
                                      @RequestParam("search") Optional<String> search,
                                      HttpServletRequest request){
        int pageIndex = page.orElse(1);
        int pageSize = size.orElse(5);
        String searchField = search.orElse(null);
        Page<SubdomainIpDto> resultPage = this.service.findAllPage(PageRequest.of(pageIndex-1,pageSize, Sort.by("id").descending()));
        if(searchField!=null){
            searchField = URLDecoder.decode(searchField);
            resultPage = this.service.searchPage(PageRequest.of(pageIndex-1,pageSize,Sort.by("id").descending()),searchField);
        }
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
//        model.addAttribute("campuses", campusService.getAll().stream().filter(c->!c.getCenterCampus()).collect(Collectors.toList()));
//        model.addAttribute("listValidate", new ArrayList<>(this.userService.findAllUserNameAndEmail()));
//        model.addAttribute("userModal", new UserModal());
        model.addAttribute("searchField", searchField);
//        model.addAttribute("roles",roleService.getAllRoles().stream().filter(x->!x.getName().equalsIgnoreCase("ROLE_USER")).collect(Collectors.toList()));
        return "subdomain-ip";
    }
}
