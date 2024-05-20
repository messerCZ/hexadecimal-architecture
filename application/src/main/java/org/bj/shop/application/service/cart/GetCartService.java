package org.bj.shop.application.service.cart;

import org.bj.shop.application.port.in.cart.GetCartUseCase;
import org.bj.shop.application.port.out.persistence.CartRepository;
import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.customer.CustomerId;

import java.util.Objects;

public class GetCartService implements GetCartUseCase {

    private final CartRepository cartRepository;

    public GetCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCart(CustomerId customerId) {
        Objects.requireNonNull(customerId, "'customerId' must not be null");

        return cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));
    }
}
