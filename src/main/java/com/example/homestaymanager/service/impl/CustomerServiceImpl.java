package com.example.homestaymanager.service.impl;

import com.example.homestaymanager.dto.response.CustomerResponse;
import com.example.homestaymanager.model.Customer;

import com.example.homestaymanager.repository.CustomerRepository;

import com.example.homestaymanager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Integer createCustomer(Customer customer){
        customerRepository.save(customer);
        return customer.getId();
    }

    @Override
    public CustomerResponse getCustomerByID(int id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CustomerResponse res = new CustomerResponse();
        res.setId(customer.getId());
        res.setEmail(customer.getEmail());
        res.setName(customer.getName());
        res.setPhone(customer.getPhone());
        res.setAddress(customer.getAddress());
        res.setImage(customer.getImage());

        return res;
    }

    @Override
    public void deleteCustomerById(int id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }
}