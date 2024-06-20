package gr.codehub.eshop.mapper;

import gr.codehub.eshop.dto.ProductDto;
import gr.codehub.eshop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface ProductMapper {
    @Mapping(target = "quantity",source = "repoQuantity")
    ProductDto toProductDto(Product product);

    @Mapping(target = "repoQuantity",source = "quantity")
    Product toProduct(ProductDto productDto);
}
