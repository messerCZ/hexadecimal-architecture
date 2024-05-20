package org.bj.shop.application.port.in.cart;

import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.cart.NotEnoughItemsInStockException;
import org.bj.shop.model.customer.CustomerId;
import org.bj.shop.model.product.ProductId;

public interface AddToCartUseCase {

    Cart addToCart(CustomerId customerId, ProductId productId, int quantity) throws ProductNotFountException, NotEnoughItemsInStockException;
}
