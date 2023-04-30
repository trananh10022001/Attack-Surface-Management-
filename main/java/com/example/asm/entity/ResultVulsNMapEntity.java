package com.example.asm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "result_vuls_nmap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultVulsNMapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "output")
    private String outPut;
    @ManyToOne(targetEntity = SubdomainIpEntity.class,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "id_ip")
    private SubdomainIpEntity subdomainIp;
}
