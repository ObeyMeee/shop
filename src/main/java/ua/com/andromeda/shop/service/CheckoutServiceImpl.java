package ua.com.andromeda.shop.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.andromeda.shop.dto.PaymentInfo;
import ua.com.andromeda.shop.dto.Purchase;
import ua.com.andromeda.shop.dto.PurchaseResponse;
import ua.com.andromeda.shop.entity.Customer;
import ua.com.andromeda.shop.entity.Order;
import ua.com.andromeda.shop.entity.OrderItem;
import ua.com.andromeda.shop.repository.CustomerRepository;

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
        parameters.put("payment_method_types", List.of("card"));
        parameters.put("description", "Andromeda purchase");
    }
}
