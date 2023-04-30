package com.example.asm.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "result_nuclei")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultNucleiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "output")
    private String output;
    @ManyToOne(targetEntity = SubdomainIpEntity.class,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "id_ip")
    private SubdomainIpEntity subdomainIp;
}
