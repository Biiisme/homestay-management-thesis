package com.example.homestaymanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private BigDecimal salary;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private  String password;

    @Column(nullable = false)
    private  String phone;
    private  String address;
    private  String image;
    @ManyToOne @JoinColumn(name = "role_id")
    private Role role;

}
