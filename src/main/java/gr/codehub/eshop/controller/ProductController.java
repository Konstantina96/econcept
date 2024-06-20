package gr.codehub.eshop.controller;

import gr.codehub.eshop.dto.CartDto;
import gr.codehub.eshop.dto.ProductDto;
import gr.codehub.eshop.dto.ResponseResult;
import gr.codehub.eshop.model.Product;
import gr.codehub.eshop.service.EshopService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value="/api")
public class ProductController {

    private EshopService eshopService;

    @GetMapping(value="" )
    public String ping (){
        return "ok";
    }

    @GetMapping(value="/product" )
    public List<ProductDto>  getAllProducts(@RequestParam(required = false) String pageCount, @RequestParam(required = false) String pageSize  ){
        return  eshopService.readProduct( pageCount,  pageSize );
    }

    @PostMapping(value="/product" )
    public Product createProduct (@RequestBody Product product){
        return eshopService.createProduct(product);
    }

    @PostMapping(value="/cart/customer/{customerId}" )
    public ResponseResult<Integer> createCart (@PathVariable("customerId") int customerId){
        return eshopService.createCart(customerId);
    }

    @PostMapping(value="/cart/{cartId}/product/{productId}" )
    public boolean addProductToBasket (@PathVariable("cartId") int cartId,    @PathVariable("productId") int productId  ){
        return eshopService.addToCart(cartId, productId);
    }

    @GetMapping(value="/cart/{cartId}")
    public List<ProductDto> productsInCart(@PathVariable("cartId") int cartId){
        return eshopService.productsInCart(cartId);
    }

    @GetMapping(value="/customer/{customerId}/cart")
    public ResponseResult<List<CartDto>> customerCartProducts(@PathVariable("customerId") int customerId){
        return eshopService.customerCartProducts(customerId);
    }

    @DeleteMapping(value="/customer/{customerId}/delete")
    public ResponseResult<Boolean> deleteCustomerCart(@PathVariable("customerId") int customerId){
        return eshopService.deleteCustomerCart(customerId);
    }
}