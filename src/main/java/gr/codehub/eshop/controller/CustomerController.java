package gr.codehub.eshop.controller;

import gr.codehub.eshop.model.Customer;
import gr.codehub.eshop.service.EshopService;
import gr.codehub.eshop.dto.ResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value="/api/customer")
public class CustomerController {
    private EshopService eshopService;

    @GetMapping(value="" )
    public ResponseResult<List<Customer>> get (){
        return eshopService.readCustomer();
    }

    @GetMapping(value="/{customerId}" )
    public ResponseResult<Customer> get (@PathVariable("customerId") int customerId){
        return eshopService.readCustomer(customerId);
    }

    @PostMapping(value="")
    public Customer create(@RequestBody Customer customer)
    {
        return eshopService.createCustomer(customer);
    }

    @PutMapping(value="/{customerId}")
    public Customer update(@PathVariable("customerId") int customerId, @RequestBody Customer customer )
    {
        return eshopService.updateCustomer(customerId,customer);
    }

    @DeleteMapping(value="/{customerId}" )
    public ResponseResult<Boolean> delete (@PathVariable("customerId") int customerId){
        return eshopService.deleteCustomer(customerId);
    }
}