package com.example.homestaymanager.dto.request;

import com.example.homestaymanager.model.Role;


import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployee {
    private String name;
    private BigDecimal salary;
    private String email;
    private String username;
    private  String phone;
    private  String address;
    private  String image;
    private Boolean active;
    private Integer roleId;
}
