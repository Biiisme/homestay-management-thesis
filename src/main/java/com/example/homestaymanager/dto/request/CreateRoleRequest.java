package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class CreateRoleRequest {
    private String name;
    private String description;
}