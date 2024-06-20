package gr.codehub.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private int cartId;
    private LocalDateTime date;
    private List<ProductDto> products;

    public CartDto addList(){
        products = new ArrayList<>();
        return this;
    }
}