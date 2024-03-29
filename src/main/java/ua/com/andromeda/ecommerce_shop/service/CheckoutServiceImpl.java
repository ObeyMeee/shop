package ua.com.andromeda.ecommerce_shop.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.andromeda.ecommerce_shop.dto.PaymentInfo;
import ua.com.andromeda.ecommerce_shop.dto.Purchase;
import ua.com.andromeda.ecommerce_shop.dto.PurchaseResponse;
import ua.com.andromeda.ecommerce_shop.entity.Customer;
import ua.com.andromeda.ecommerce_shop.entity.Order;
import ua.com.andromeda.ecommerce_shop.entity.OrderItem;
import ua.com.andromeda.ecommerce_shop.repository.CustomerRepository;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.secret.key}") String secretKey) {
        this.customerRepository = customerRepository;
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        String trackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(trackingNumber);
        setOrderItems(purchase, order);
        Customer customer = getCustomer(purchase);
        customer.add(order);
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());
        customerRepository.save(customer);
        return new PurchaseResponse(trackingNumber);
    }

    private static void setOrderItems(Purchase purchase, Order order) {
        Set<OrderItem> items = purchase.getOrderItems();
        items.forEach(order::add);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    private Customer getCustomer(Purchase purchase) {
        Customer customer = purchase.getCustomer();
        Customer customerFromDatabase = customerRepository.findByEmail(customer.getEmail());
        return Objects.requireNonNullElse(customerFromDatabase, customer);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        Map<String, Object> parameters = new HashMap<>();
        setParameters(parameters, paymentInfo);
        return PaymentIntent.create(parameters);
    }

    private void setParameters(Map<String, Object> parameters, PaymentInfo paymentInfo) {
        parameters.put("amount", paymentInfo.amount());
        parameters.put("currency", paymentInfo.currency());
        parameters.put("receipt_email", paymentInfo.receiptEmail());
        List<String> list = List.of("card");
        parameters.put("payment_method_types", list);
        parameters.put("description", "Andromeda purchase");
    }
}
