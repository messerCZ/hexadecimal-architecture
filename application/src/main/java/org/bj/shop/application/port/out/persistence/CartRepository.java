package org.bj.shop.application.port.out.persistence;

import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.customer.CustomerId;

import java.util.Optional;

public interface CartRepository {

    void save(Cart cart);

    Optional<Cart> findByCustomerId(CustomerId customerId);

    void deleteByCustomerId(CustomerId customerId);
}
