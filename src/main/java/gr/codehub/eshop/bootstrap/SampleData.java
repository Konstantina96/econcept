package gr.codehub.eshop.bootstrap;

import gr.codehub.eshop.model.Product;
import gr.codehub.eshop.service.EshopService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@AllArgsConstructor
public class SampleData {

    private final EshopService eshopService;


    @Bean
    public CommandLineRunner myCommandLineRunner() {
        return this::run;
    }

    private void run(String... args) {

        Product p1 = new Product();
        p1.setName("Like a sunset");
        p1.setCategory("women");
        p1.setPrice(50.0);
        p1.setOld_price(100.0);
        eshopService.createProduct(p1);

        Product p2 = new Product();
        p2.setName("The opposites");
        p2.setCategory("women");
        p2.setPrice(65.0);
        p2.setOld_price(120.0);
        eshopService.createProduct(p2);

        Product p3 = new Product();
        p3.setName("For two");
        p3.setCategory("women");
        p3.setPrice(60.0);
        p3.setOld_price(100.0);
        eshopService.createProduct(p3);


    }
}
