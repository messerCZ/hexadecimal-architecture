package org.bj.shop.application.service.cart;

import org.bj.shop.application.port.out.persistence.CartRepository;
import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.cart.NotEnoughItemsInStockException;
import org.bj.shop.model.customer.CustomerId;
import org.bj.shop.model.product.Product;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bj.shop.model.money.TestMoneyFactory.euros;
import static org.bj.shop.model.product.TestProductFactory.createTestProduct;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCartServiceTest {

    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final GetCartService getCartService = new GetCartService(cartRepository);

    @Test
    void givenCartIsPersisted_getCart_returnsPersistedCart() throws NotEnoughItemsInStockException {
        Cart persistedCart = new Cart(TEST_CUSTOMER_ID);
        persistedCart.addProduct(TEST_PRODUCT_1, 1);
        persistedCart.addProduct(TEST_PRODUCT_2, 5);

        when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.of(persistedCart));

        Cart cart = getCartService.getCart(TEST_CUSTOMER_ID);

        assertThat(cart).isSameAs(persistedCart);
    }

    @Test
    void givenCartIsNotPersisted_getCart_returnsAnEmptyCart() {
        when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.empty());

        Cart cart = getCartService.getCart(TEST_CUSTOMER_ID);

        assertThat(cart).isNotNull();
        assertThat(cart.lineItems()).isEmpty();
    }
}
