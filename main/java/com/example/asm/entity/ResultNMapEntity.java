package com.example.asm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "result_nmap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultNMapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "port_number")
    private int portNumber;
    @Column(name = "status")
    private String status;
    @Column(name = "protocol")
    private String protocol;
    @ManyToOne(targetEntity = SubdomainIpEntity.class,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "id_ip")
    private SubdomainIpEntity subdomainIp;
}
