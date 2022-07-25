package ua.com.andromeda.shop.service;

import ua.com.andromeda.shop.dto.Purchase;
import ua.com.andromeda.shop.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
