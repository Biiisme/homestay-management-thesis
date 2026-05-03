package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.model.Branch;

import com.example.homestaymanager.service.BranchService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping("/branches")
    public ApiResponse<Integer> createBranch(@RequestBody Branch branch){

        int i = branchService.createBranch(branch);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED,branch.getId());
    }

    @GetMapping("/branches/{branchesId}")
    public ApiResponse<Branch> getBranchById(@PathVariable int branchId){
        Branch branch=  branchService.getBranchByID(branchId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,branch);


    }

    @DeleteMapping("/branches/{branchesId}")
    public  ApiResponse<?>  deleteEmployeeById(@PathVariable int branchId){

        branchService.deleteBranchById(branchId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }

}
