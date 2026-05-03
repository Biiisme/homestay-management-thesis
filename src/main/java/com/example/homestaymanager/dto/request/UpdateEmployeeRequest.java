package com.example.homestaymanager.dto.request;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateEmployeeRequest {
    private String name;
    private BigDecimal salary;
    private String email;
    private String phone;
    private String address;
    private String image;
    private Integer roleId;
}
