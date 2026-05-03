package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.dto.response.CustomerResponse;
import com.example.homestaymanager.model.Customer;
import com.example.homestaymanager.model.Employee;
import com.example.homestaymanager.service.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/customers")
    public ApiResponse<Integer> createCustomer(@RequestBody Customer customer){
        int id = customerService.createCustomer(customer);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED,id);
    }

    @GetMapping("/customers/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable int id){

        CustomerResponse customer= customerService.getCustomerByID(id);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,customer);
    }

    @DeleteMapping("/customers/{id}")
    public ApiResponse<?> deleteCustomerById(@PathVariable int id){

        customerService.deleteCustomerById(id);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }
}
