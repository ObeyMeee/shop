package ua.com.andromeda.shop.dto;

import lombok.Data;
import ua.com.andromeda.shop.entity.Address;
import ua.com.andromeda.shop.entity.Customer;
import ua.com.andromeda.shop.entity.Order;
import ua.com.andromeda.shop.entity.OrderItem;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
