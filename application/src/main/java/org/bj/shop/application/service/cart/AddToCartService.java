package org.bj.shop.application.service.cart;

import org.bj.shop.application.port.in.cart.AddToCartUseCase;
import org.bj.shop.application.port.in.cart.ProductNotFountException;
import org.bj.shop.application.port.out.persistence.CartRepository;
import org.bj.shop.application.port.out.persistence.ProductRepository;
import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.cart.NotEnoughItemsInStockException;
import org.bj.shop.model.customer.CustomerId;
import org.bj.shop.model.product.Product;
import org.bj.shop.model.product.ProductId;

import java.util.Objects;

public class AddToCartService implements AddToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public AddToCartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(CustomerId customerId, ProductId productId, int quantity) throws ProductNotFountException, NotEnoughItemsInStockException {
        Objects.requireNonNull(customerId, "'customerId' must not be null");
        Objects.requireNonNull(productId, "'productId' must not be null");
        if (quantity < 1) {
            throw new IllegalArgumentException("'quantity' must be greater than 0");
        }

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFountException::new);
        Cart cart = cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));

        cart.addProduct(product, quantity);

        cartRepository.save(cart);

        return cart;
    }
}
