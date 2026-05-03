package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String image;
}
