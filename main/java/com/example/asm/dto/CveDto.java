package com.example.asm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CveDto {
    private Long id;
    private String cveId;
    private float cvssPoint;
    private String descriptions;
    private String webTech;
    private String link;
    private DomainDto domain;
}
