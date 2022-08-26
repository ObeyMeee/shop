package ua.com.andromeda.ecommerce_shop.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import ua.com.andromeda.ecommerce_shop.dto.PaymentInfo;
import ua.com.andromeda.ecommerce_shop.dto.Purchase;
import ua.com.andromeda.ecommerce_shop.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
