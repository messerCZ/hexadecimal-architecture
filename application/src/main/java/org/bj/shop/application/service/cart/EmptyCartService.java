package org.bj.shop.application.service.cart;

import org.bj.shop.application.port.in.cart.EmptyCartUseCase;
import org.bj.shop.application.port.out.persistence.CartRepository;
import org.bj.shop.model.customer.CustomerId;

import java.util.Objects;

public class EmptyCartService implements EmptyCartUseCase {

    public final CartRepository cartRepository;

    public EmptyCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void emptyCart(CustomerId customerId) {
        Objects.requireNonNull(customerId, "'customerId' must not be null");

        cartRepository.deleteByCustomerId(customerId);
    }
}
