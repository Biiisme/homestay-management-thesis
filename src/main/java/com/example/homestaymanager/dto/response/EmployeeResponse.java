package com.example.homestaymanager.dto.response;
import com.example.homestaymanager.model.Role;
import lombok.Data;

@Data
public class EmployeeResponse {

    private int id;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String address;
    private String image;
    private java.math.BigDecimal salary;
    private Boolean active;
    private Role role;
}
