package gr.codehub.eshop.service;

import gr.codehub.eshop.dto.ResponseResult;
import gr.codehub.eshop.dto.ResponseStatus;
import gr.codehub.eshop.dto.CartDto;
import gr.codehub.eshop.dto.ProductDto;
import gr.codehub.eshop.model.Cart;
import gr.codehub.eshop.model.CartProduct;
import gr.codehub.eshop.model.Customer;
import gr.codehub.eshop.model.Product;
import gr.codehub.eshop.repository.*;
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
public class EshopServiceImpl implements EshopService {

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private CartProductRepository cartProductRepository;

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
    public ResponseResult<Customer> readCustomer(int customerId) {
        return new ResponseResult<>(customerRepository.findById(customerId).get(), ResponseStatus.SUCCESS, "Success");
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
        return productRepository.findById(productId).get();
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
            cartRepository.save(cart);
        } catch (Exception e) {
            log.debug("Create cart method returning from method for {}", ResponseStatus.CART_NOT_CREATED);
            return new ResponseResult<>(-1, ResponseStatus.CART_NOT_CREATED, "The cart has NOT been saved");
        }

        log.debug("Create cart method returning from method for {}", ResponseStatus.SUCCESS);
        return new ResponseResult<>(cart.getId(),
                ResponseStatus.SUCCESS, "The cart has been created successfully");
    }

    @Override
    public boolean addToCart(int cartId, int productId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        Optional<Product> productOpt = productRepository.findById(productId);
        if (cartOpt.isEmpty() || productOpt.isEmpty())
            return false;

        Optional<CartProduct> cartProductOpt
                = cartProductRepository.findByCartAndProduct(cartId, productId);

        if (cartProductOpt.isEmpty()) {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setCart(cartOpt.get());
            cartProduct.setProduct(productOpt.get());
            cartProductRepository.save(cartProduct);
            return true;
        }
        cartProductOpt.get().setQuantity(cartProductOpt.get().getQuantity() + 1);
        cartProductRepository.save(cartProductOpt.get());
        return true;
    }

    @Override
    public List<ProductDto> productsInCart(int cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty())
            return null;

        List<Product> products = cartRepository.getProductsFromCart(cartId);
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
    public ResponseResult<List<CartDto>> customerCartProducts(int customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {

            log.debug("Create cart method returning from method for {}", ResponseStatus.CUSTOMER_NOT_FOUND);
            return new ResponseResult<>(null,
                    ResponseStatus.CUSTOMER_NOT_FOUND, "The customer cannot be found");
        }
        List<Cart> cartList = cartRepository.findCartByCustomerId(customerId);

        List<CartDto> cartDtoList = cartList
                .stream()
                .map(cart -> new CartDto(cart.getId(), cart.getDate(), null).addList())
                .toList();
        return new ResponseResult<>(cartDtoList, ResponseStatus.SUCCESS, "All OK");
    }

    @Override
    public ResponseResult<Boolean> deleteCustomerCart(int customerId) {
        return null;
    }
}
