package ua.com.andromeda.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.andromeda.shop.dto.Purchase;
import ua.com.andromeda.shop.dto.PurchaseResponse;
import ua.com.andromeda.shop.service.CheckoutService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        System.out.println(purchase);
        return checkoutService.placeOrder(purchase);
    }

}
