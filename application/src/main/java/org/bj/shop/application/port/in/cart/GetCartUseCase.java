package org.bj.shop.application.port.in.cart;

import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.customer.CustomerId;

public interface GetCartUseCase {

    Cart getCart(CustomerId customerId);
}
