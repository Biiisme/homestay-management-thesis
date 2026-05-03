package com.example.homestaymanager.dto.response;
import lombok.Data;

@Data
public class EmployeeResponse {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String image;

    private RoleResponse role; // nested object
}