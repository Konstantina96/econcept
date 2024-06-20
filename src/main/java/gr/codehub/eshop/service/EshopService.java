package gr.codehub.eshop.service;

import gr.codehub.eshop.dto.ResponseResult;
import gr.codehub.eshop.dto.CartDto;
import gr.codehub.eshop.dto.ProductDto;
import gr.codehub.eshop.model.Customer;
import gr.codehub.eshop.model.Product;

import java.util.List;

public interface EshopService {
    Customer createCustomer(Customer customer);
    ResponseResult<List<Customer>> readCustomer();
    ResponseResult<Customer> readCustomer(int customerId);
    Customer updateCustomer(int customerId, Customer customer);
    ResponseResult<Boolean> deleteCustomer(int customerId);

    Product createProduct(Product product);
    List<ProductDto> readProduct();
    List<ProductDto> readProduct(String pageCount, String pageSize);
    Product readProduct(int productId);
    Product updateProduct(int productId, Product product);
    boolean deleteProduct(int productId);

    ResponseResult<Integer> createCart(int customerId);
    boolean addToCart(int cartId, int productId);
    List<ProductDto> productsInCart(int cartId);

    ResponseResult<List<CartDto>> customerCartProducts(int customerId);

    ResponseResult<Boolean> deleteCustomerCart(int customerId);
}
