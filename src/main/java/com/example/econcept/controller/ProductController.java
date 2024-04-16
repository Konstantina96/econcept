package com.example.econcept.controller;

import com.example.econcept.dto.CartDto;
import com.example.econcept.dto.ProductDto;
import com.example.econcept.dto.ResponseResult;
import com.example.econcept.model.Product;
import com.example.econcept.service.EconceptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {


    private EconceptService econcept;

    @PostMapping(value="/product" )
    public Product createProduct (@RequestBody Product product){
        return econcept.createProduct(product);
    }


    @PostMapping(value="/basket/customer/{customerId}" )
    public ResponseResult<Integer> createCart (@PathVariable("customerId") int customerId){
        return econcept.createCart(customerId);
    }

    @PostMapping(value="/basket/{basketId}/product/{productId}" )
    public boolean addProductToBasket (@PathVariable("CartId") int CartId,    @PathVariable("productId") int productId  ){
        return econcept.addToCart(CartId, productId);
    }

    @GetMapping(value="/product" )
    public List<ProductDto>  getAllProducts(@RequestParam(required = false) String pageCount, @RequestParam(required = false) String pageSize  ){
        return  econcept.readProduct( pageCount,  pageSize );
    }

    @GetMapping(value="/basket/{basketId}")
    public List<ProductDto> productsInBasket(@PathVariable("basketId") int CartId){
        return econcept.productsInCart(CartId);
    }

    @GetMapping(value="/Cart1/{CartId}")
    public List<ProductDto> productsInBasket1(@PathVariable("CartId") int CartId){
        return econcept.productsInCart1(CartId);
    }

    @GetMapping(value="/customer/{customerId}/basket")
    public ResponseResult<List<CartDto>> customerBasketProducts(@PathVariable("customerId") int customerId){
        return econcept.customerBasketProducts(customerId);
    }

    @GetMapping(value="/customer/{customerId}/delete")
    public ResponseResult<Boolean> deleteCustomerBasket(@PathVariable("customerId") int customerId){
        return econcept.deleteCustomerCart(customerId);
    }

}
