package ua.com.andromeda.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.andromeda.shop.dto.Purchase;
import ua.com.andromeda.shop.dto.PurchaseResponse;
import ua.com.andromeda.shop.entity.Customer;
import ua.com.andromeda.shop.entity.Order;
import ua.com.andromeda.shop.entity.OrderItem;
import ua.com.andromeda.shop.repository.CustomerRepository;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();

        String trackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(trackingNumber);

        Set<OrderItem> items = purchase.getOrderItems();
        items.forEach(order::add);

        Customer customer = purchase.getCustomer();

        Customer customerFromDatabase = customerRepository.findByEmail(customer.getEmail());

        if (customerFromDatabase != null) {
            customer = customerFromDatabase;
        }
        customer.add(order);

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        customerRepository.save(customer);
        return new PurchaseResponse(trackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
