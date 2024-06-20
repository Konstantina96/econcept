package gr.codehub.eshop.mapper;

import gr.codehub.eshop.dto.CartDto;
import gr.codehub.eshop.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toCartDto(Cart cart);
    Cart toCart(CartDto cartDto);
}
