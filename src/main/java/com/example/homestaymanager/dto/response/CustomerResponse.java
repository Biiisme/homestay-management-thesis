package com.example.homestaymanager.dto.response;

import lombok.Data;

@Data
public class CustomerResponse {

    private int id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String image;
}
