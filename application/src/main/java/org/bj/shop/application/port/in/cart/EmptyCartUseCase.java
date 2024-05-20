package org.bj.shop.application.port.in.cart;

import org.bj.shop.model.customer.CustomerId;

public interface EmptyCartUseCase {

    void emptyCart(CustomerId customerId);
}
