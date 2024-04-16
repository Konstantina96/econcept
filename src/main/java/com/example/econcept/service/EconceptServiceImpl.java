package com.example.econcept.service;




import com.example.econcept.dto.ResponseResult;
import com.example.econcept.dto.ResponseStatus;
import com.example.econcept.dto.CartDto;
import com.example.econcept.dto.ProductDto;
import com.example.econcept.model.Cart;
import com.example.econcept.model.CartProduct;
import com.example.econcept.model.Customer;
import com.example.econcept.model.Product;
import com.example.econcept.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
@Slf4j
public class EconceptServiceImpl implements EconceptService {

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private CartRepository basketRepository;
    private CartProductRepository basketProductRepository;

    private ProductRepositoryPageable productPaging;

    @Override
    public Customer createCustomer(Customer customer) {
        customer.setRegistrationDate(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    @Override
    public ResponseResult<List<Customer>> readCustomer() {
        List<Customer> list = customerRepository.findAll();
        if (list.isEmpty())
            return new ResponseResult<>(list, ResponseStatus.CUSTOMER_NOT_FOUND, "No customers");

        return new ResponseResult<>(list, ResponseStatus.SUCCESS, "Success");
    }

    @Override
    public Customer readCustomer(int customerId) {
        return customerRepository.findById(customerId).get();
    }

    @Override
    public Customer updateCustomer(int customerId, Customer customer) {
        Optional<Customer> customerDb = customerRepository.findById(customerId);
        if (customerDb.isEmpty())
            return null;
        customerDb.get().setEmail(customer.getEmail());
        return customerRepository.save(customerDb.get());
    }

    @Override
    public ResponseResult<Boolean> deleteCustomer(int customerId) {
        Optional<Customer> customerDb = customerRepository.findById(customerId);
        if (customerDb.isEmpty())
            return new ResponseResult<>(false, ResponseStatus.CUSTOMER_NOT_FOUND, "No such customer");
        try {
            customerRepository.delete(customerDb.get());
            return new ResponseResult<>(true, ResponseStatus.SUCCESS, "The customer has been deleted");
        } catch (Exception e) {
            return new ResponseResult<>(false, ResponseStatus.CUSTOMER_CANNOT_BE_DELETED, "The customer cannot be deleted");
        }
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<ProductDto> readProduct() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(), 0))
                .toList();
    }

    @Override
    public List<ProductDto> readProduct(String pageCount, String pageSize) {

        int pCount;
        int pSize;
        try {
            pCount = Integer.parseInt(pageCount);
            pSize = Integer.parseInt(pageSize);
        } catch (Exception e) {
            pCount = 1;
            pSize = 20;
        }

        Pageable firstPageWithTwoElements = PageRequest.of(pCount - 1, pSize);

        return productPaging.findAll(firstPageWithTwoElements).stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(), 0)
                )
                .toList();
    }

    @Override
    public Product readProduct(int productId) {
        return null;
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        return null;
    }

    @Override
    public boolean deleteProduct(int productId) {
        return false;
    }


    @Override
    public ResponseResult<Integer> createCart(int customerId) {
        log.debug("Create cart method entering method for {}", customerId);
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {

            log.debug("Create cart method returning from method for {}", ResponseStatus.CUSTOMER_NOT_FOUND);
            return new ResponseResult<>(-1,
                    ResponseStatus.CUSTOMER_NOT_FOUND, "The customer cannot be found");
        }
        Cart cart = new Cart();
        cart.setDate(LocalDateTime.now());
        cart.setCustomer(customerOpt.get());
        try {
            basketRepository.save(cart);
        } catch (Exception e) {
            log.debug("Create basket method returning from method for {}", ResponseStatus.BASKET_NOT_CREATED);
            return new ResponseResult<>(-1, ResponseStatus.BASKET_NOT_CREATED, "The basket has NOT been saved");
        }

        log.debug("Create basket method returning from method for {}", ResponseStatus.SUCCESS);
        return new ResponseResult<>(cart.getId(),
                ResponseStatus.SUCCESS, "The basket has been created successfully");
    }

    @Override
    public boolean addToCart(int basketId, int productId) {
        return false;
    }

    @Override
    public List<ProductDto> productsInCart(int basketId) {
        return null;
    }

    @Override
    public List<ProductDto> productsInCart1(int basketId) {
        return null;
    }

    @Override
    public boolean addToBasket(int basketId, int productId) {

        Optional<Cart> basketOpt = basketRepository.findById(basketId);
        Optional<Product> productOpt = productRepository.findById(productId);
        if (basketOpt.isEmpty() || productOpt.isEmpty())
            return false;

        Optional<CartProduct> basketProductOpt
                = basketProductRepository.findByBasketAndProduct(basketId, productId);

        if (basketProductOpt.isEmpty()) {
            CartProduct basketProduct = new CartProduct();
            basketProduct.setBasket(basketOpt.get());
            basketProduct.setProduct(productOpt.get());
            basketProductRepository.save(basketProduct);
            return true;
        }
        basketProductOpt.get().setQuantity(basketProductOpt.get().getQuantity() + 1);
        basketProductRepository.save(basketProductOpt.get());

        return true;
    }

    @Override
    public List<ProductDto> productsInBasket(int basketId) {
        Optional<Cart> basketOpt = basketRepository.findById(basketId);
        if (basketOpt.isEmpty())
            return null;
        //todo

        //    List<Product> products = productRepository.findAllProduct(basketId);
        List<Product> products = basketRepository.getProductsFromBasket(basketId);
        return products
                .stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(), 0)
                )
                .toList();
    }

    @Override
    public List<ProductDto> productsInBasket1(int basketId) {
        Optional<Cart> basketOpt = basketRepository.findById(basketId);
        if (basketOpt.isEmpty())
            return null;
        //todo

        List<Product> products = productRepository.findAllProduct(basketId);

        return products
                .stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(), 0)
                )
                .toList();
    }

    @Override
    public ResponseResult<List<CartDto>> customerBasketProducts(int customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {

            log.debug("Create basket method returning from method for {}", ResponseStatus.CUSTOMER_NOT_FOUND);
            return new ResponseResult<>(null,
                    ResponseStatus.CUSTOMER_NOT_FOUND, "The customer cannot be found");
        }
        List<Cart> basketList = basketRepository.findCartByCustomerId(customerId);

        List<CartDto> basketDtoList = basketList
                .stream()
                .map(basket -> new CartDto(basket.getId(), basket.getDate(), null).addList())
                .toList();
        return new ResponseResult<>(basketDtoList, ResponseStatus.SUCCESS, "All OK");

    }

    @Override
    public ResponseResult<Boolean> deleteCustomerBasket(int customerId) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> deleteCustomerCart(int customerId) {
        return null;
    }
}
