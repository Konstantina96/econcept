package gr.codehub.eshop.mapper;

import gr.codehub.eshop.dto.CustomerDto;
import gr.codehub.eshop.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toCustomerDto(Customer customer);
    Customer toCustomer(CustomerDto customerDto);
}
