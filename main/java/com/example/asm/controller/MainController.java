package com.example.asm.controller;

import com.example.asm.dto.CveDto;
import com.example.asm.service.ICveService;
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
public class MainController {
    @Autowired
    ICveService service;
    @GetMapping
    public String showForm(){
        return "domain-form";
    }
    @PostMapping(value = "submit")
    public String submitForm(){
        return "redirect:/domain";
    }
}
