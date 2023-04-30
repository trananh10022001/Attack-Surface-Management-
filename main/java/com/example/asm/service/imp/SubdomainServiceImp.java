package com.example.asm.service.imp;

import com.example.asm.dto.*;
import com.example.asm.entity.DomainEntity;
import com.example.asm.entity.SubdomainIpEntity;
import com.example.asm.mapper.*;
import com.example.asm.repository.*;
import com.example.asm.service.ExportExcelSubdomain;
import com.example.asm.service.ISubdomainService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubdomainServiceImp implements ISubdomainService {
    @Autowired
    ISubdomainRepository repository;
    @Autowired
    ISubdomainIpRepository subdomainIpRepository;
    @Autowired
    IResultNMapRepository resultNMapRepository;
    @Autowired
    IResultHttpxRepository resultHttpxRepository;
    @Autowired
    IResultNucleiRepository resultNucleiRepository;
    @Autowired
    IResultVulsNMapRepository resultVulsNMapRepository;
    @Autowired
    IDomainRepository domainRepository;

    @Autowired
    SubdomainMapper mapper;
    @Autowired
    SubdomainIpMapper subdomainIpMapper;
    @Autowired
    ResultNMapMapper resultNMapMapper;
    @Autowired
    ResultHttpxMapper resultHttpxMapper;
    @Autowired
    ResultNucleiMapper resultNucleiMapper;
    @Override
    public Page<SubdomainDto> findAllPage(Pageable pageable) {
        return this.repository.findAll(pageable).map(x->mapper.toDto(x));
    }

    @Override
    public Page<SubdomainDto> searchPage(Pageable pageable, String search) {
        return this.repository.searchAllBy(pageable,search).map(mapper::toDto);
    }

    @Override
    public Page<SubdomainDto> searchPageByDomainId(Pageable pageable, Integer id) {
        return this.repository.searchAllByDomainId(pageable, id).map(mapper::toDto);
    }

    public List<Integer> checkDifferentBetweenScan(){
        try {
            List<Integer> idList = new ArrayList<>();
            List<DomainEntity> lastTwoDomain = domainRepository.getLastTwoDomain();
            List<SubdomainDto> lastScanDomain = repository.getAllByDomainId(lastTwoDomain.get(0).getId()).stream().map(x -> mapper.toDto(x)).collect(Collectors.toList());
            List<SubdomainDto> beforeLastScanDomain = repository.getAllByDomainId(lastTwoDomain.get(1).getId()).stream().map(x -> mapper.toDto(x)).collect(Collectors.toList());
            List<String> subdomainName = beforeLastScanDomain.stream().map(SubdomainDto::getSubdomainName).collect(Collectors.toList());
            if (lastScanDomain.size() != beforeLastScanDomain.size()){
                idList = lastScanDomain.stream().map(SubdomainDto::getId).collect(Collectors.toList());
                return idList;
            }
            for (SubdomainDto subdomainDto : lastScanDomain) {
                if (!subdomainName.contains(subdomainDto.getSubdomainName())){
                    idList.add(subdomainDto.getId());
                }
            }
            return idList;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  new ArrayList<>();
    }

    @Override
    public Map<String, Object> getMoreInformation(Integer id) {
        Map<String, Object> result = new HashMap<>();
        SubdomainDto subdomainDto = mapper.toDto(repository.findById(id).orElseThrow());
        SubdomainIpDto subdomainIpDto = subdomainIpMapper.toDto(subdomainIpRepository.findBySubdomainId(id));
        List<ResultNMapDto> resultNMapDtos = resultNMapRepository.getALlBySubdomainIp(subdomainIpDto.getId()).stream().map(x -> resultNMapMapper.toDto(x)).collect(Collectors.toList());
        List<ResultHttpxDto> resultHttpxDtos = resultHttpxRepository.findByDomainIdAndSubdomainIp(subdomainDto.getDomain().getId(), subdomainIpDto.getIp()).stream().map(x -> resultHttpxMapper.toDto(x)).collect(Collectors.toList());
        List<ResultNucleiDto> resultNucleiDtos = resultNucleiRepository.findAllBySubdomainIpId(subdomainIpDto.getId()).stream().map(x -> resultNucleiMapper.toDto(x)).collect(Collectors.toList());

        Set<String> technical = new HashSet<>();
        Set<String> ports = new HashSet<>();
        List<String> vul = new ArrayList<>();
        for (ResultNMapDto resultNMapDto : resultNMapDtos) {
            ports.add(resultNMapDto.getPortNumber() + resultNMapDto.getProtocol());
        }
        for (ResultHttpxDto resultHttpxDto : resultHttpxDtos) {
            String tech = resultHttpxDto.getOutput().substring(resultHttpxDto.getOutput().indexOf("[") + 1, resultHttpxDto.getOutput().indexOf("]"));
            List<String> myList = new ArrayList<>(Arrays.asList(tech.split(",")));
            technical.addAll(myList);
        }
        for (ResultNucleiDto resultNucleiDto : resultNucleiDtos) {
            vul.add(resultNucleiDto.getOutput());
        }

        result.put("domainId", subdomainDto.getDomain().getId());
        result.put("subdomainId", id);
        result.put("subdomain", subdomainDto.getSubdomainName());
        result.put("ip", subdomainIpDto.getIp());
        result.put("port", ports);
        result.put("technical", technical);
        result.put("vul", vul);

        return result;
    }

    @Override
    public void export(Integer id, HttpServletResponse response) {
        Map<String, Object> subdomain = getMoreInformation(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=subdomain_" + id + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ExportExcelSubdomain exportExcelSubdomain = new ExportExcelSubdomain(subdomain);
        try {
            exportExcelSubdomain.export(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteById(Integer id) {
        try {
            SubdomainDto subdomainDto = mapper.toDto(repository.findById(id).orElseThrow());
            SubdomainIpEntity subdomainIpEntity = subdomainIpRepository.findBySubdomainId(subdomainDto.getId());
            if (subdomainIpEntity != null){
                resultNMapRepository.deleteBySubdomainIpId(subdomainIpEntity.getId());
                resultNucleiRepository.deleteBySubdomainIpId(subdomainIpEntity.getId());
                resultVulsNMapRepository.deleteBySubdomainIpId(subdomainIpEntity.getId());
                subdomainIpRepository.deleteBySubdomainId(subdomainDto.getId());
            }
            repository.deleteBySubdomainId(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
