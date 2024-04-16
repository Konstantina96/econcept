package com.example.econcept.controller;


import com.example.econcept.model.Customer;
import com.example.econcept.service.EconceptService;
import com.example.econcept.dto.ResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerController {
    private EconceptService econcept;

    @GetMapping(value="" )
    public String ping (){
        return "ok";
    }

    @GetMapping(value="/customer" )
    public ResponseResult<List<Customer>> get (){
        return econcept.readCustomer();
    }
    @GetMapping(value="/customer/{customerId}" )
    public Customer get (@PathVariable("customerId") int customerId){
        return econcept.readCustomer(customerId);
    }

    @PostMapping(value="/customer")
    public Customer create(@RequestBody Customer customer)
    {
        return econcept.createCustomer(customer);
    }

    @PutMapping(value="/customer/{customerId}")
    public Customer update(@PathVariable("customerId") int customerId,@RequestBody Customer customer )
    {
        return econcept.updateCustomer(customerId,customer);
    }

    @DeleteMapping(value="/customer/{customerId}" )
    public ResponseResult<Boolean> delete (@PathVariable("customerId") int customerId){
        return econcept.deleteCustomer(customerId);
    }

}