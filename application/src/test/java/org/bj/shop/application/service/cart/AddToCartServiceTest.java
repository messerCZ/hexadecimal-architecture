package org.bj.shop.application.service.cart;

import org.assertj.core.api.ThrowableAssert;
import org.bj.shop.application.port.in.cart.ProductNotFountException;
import org.bj.shop.application.port.out.persistence.CartRepository;
import org.bj.shop.application.port.out.persistence.ProductRepository;
import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.cart.NotEnoughItemsInStockException;
import org.bj.shop.model.customer.CustomerId;
import org.bj.shop.model.product.Product;
import org.bj.shop.model.product.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.bj.shop.model.money.TestMoneyFactory.euros;
import static org.bj.shop.model.product.TestProductFactory.createTestProduct;
import static org.mockito.Mockito.*;

public class AddToCartServiceTest {

    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final AddToCartService addToCartService = new AddToCartService(cartRepository, productRepository);

    @BeforeEach
    void initTestDoubles() {
        when(productRepository.findById(TEST_PRODUCT_1.id())).thenReturn(Optional.of(TEST_PRODUCT_1));
        when(productRepository.findById(TEST_PRODUCT_2.id())).thenReturn(Optional.of(TEST_PRODUCT_2));
    }

    @Test
    void givenExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned() throws ProductNotFountException, NotEnoughItemsInStockException {
        Cart persistedCart = new Cart(TEST_CUSTOMER_ID);
        persistedCart.addProduct(TEST_PRODUCT_1, 1);

        when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.of(persistedCart));

        Cart cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_2.id(), 3);

        verify(cartRepository).save(cart);

        assertThat(cart.lineItems()).hasSize(2);
        assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
        assertThat(cart.lineItems().get(0).quantity()).isEqualTo(1);
        assertThat(cart.lineItems().get(1).product()).isEqualTo(TEST_PRODUCT_2);
        assertThat(cart.lineItems().get(1).quantity()).isEqualTo(3);
    }

    @Test
    void givenNoExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned() throws NotEnoughItemsInStockException, ProductNotFountException {
        Cart cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), 2);

        verify(cartRepository).save(cart);

        assertThat(cart.lineItems()).hasSize(1);
        assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
        assertThat(cart.lineItems().get(0).quantity()).isEqualTo(2);
    }

    @Test
    void givenAnUnknownProductId_addToCart_throwsException() {
        ProductId productId = ProductId.randomProductId();

        ThrowableAssert.ThrowingCallable invocation = () -> addToCartService.addToCart(TEST_CUSTOMER_ID, productId, 1);

        assertThatExceptionOfType(ProductNotFountException.class).isThrownBy(invocation);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void givenQuantityLessThan1_addToCart_throwsException() {
        int quantity = 0;

        ThrowableAssert.ThrowingCallable invocation = () -> addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), quantity);

        assertThatIllegalArgumentException().isThrownBy(invocation);
        verify(cartRepository, never()).save(any());
    }
}
