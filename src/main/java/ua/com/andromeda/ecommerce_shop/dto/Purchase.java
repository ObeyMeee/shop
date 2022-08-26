package ua.com.andromeda.ecommerce_shop.dto;

import lombok.Data;
import ua.com.andromeda.ecommerce_shop.entity.Address;
import ua.com.andromeda.ecommerce_shop.entity.Customer;
import ua.com.andromeda.ecommerce_shop.entity.Order;
import ua.com.andromeda.ecommerce_shop.entity.OrderItem;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
