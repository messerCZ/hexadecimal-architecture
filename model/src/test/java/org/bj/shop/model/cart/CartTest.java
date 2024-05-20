package org.bj.shop.model.cart;

import org.assertj.core.api.ThrowableAssert;
import org.bj.shop.model.money.Money;
import org.bj.shop.model.product.Product;
import org.bj.shop.model.product.TestProductFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.bj.shop.model.cart.TestCartFactory.emptyCartForRandomCustomer;
import static org.bj.shop.model.money.TestMoneyFactory.euros;

public class CartTest {

    @Test
    void givenEmptyCart_addTwoProducts_productsAreInCart() throws NotEnoughItemsInStockException {
        Cart cart = emptyCartForRandomCustomer();

        Product product1 = TestProductFactory.createTestProduct(euros(12, 99));
        Product product2 = TestProductFactory.createTestProduct(euros(5, 97));

        cart.addProduct(product1, 3);
        cart.addProduct(product2, 5);

        BigDecimal subProduct1 = product1.price().amount().multiply(BigDecimal.valueOf(3));
        BigDecimal subProduct2 = product2.price().amount().multiply(BigDecimal.valueOf(5));


        assertThat(cart.numberOfItems()).isEqualTo(8);
        assertThat(cart.subTotal()).isEqualTo(Money.of(Currency.getInstance("EUR"), subProduct1.add(subProduct2)));
    }

    @Test
    void givenAProductWithAFewItemsAvailable_addMoreItemsThanAvailableToTheCart_throwsException() {
        Cart cart = emptyCartForRandomCustomer();
        Product product = TestProductFactory.createTestProduct(euros(9, 97), 3);

        ThrowableAssert.ThrowingCallable invocation = () -> cart.addProduct(product, 4);

        assertThatExceptionOfType(NotEnoughItemsInStockException.class)
                .isThrownBy(invocation)
                .satisfies(ex -> assertThat(ex.itemsInStock()).isEqualTo(product.itemsInStock()));
    }

    @Test
    void givenAProductWithAFewItemsAvailable_addAllAvailableItemsToTheCart_succeeds() {
        Cart cart = emptyCartForRandomCustomer();
        Product product = TestProductFactory.createTestProduct(euros(9, 97), 3);

        ThrowableAssert.ThrowingCallable invocation = () -> cart.addProduct(product, 3);

        assertThatNoException().isThrownBy(invocation);
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -1 , 0})
    void givenEmptyCart_addLessThanOneItemOfAProduct_throwsException(int quantity) {
        Cart cart = emptyCartForRandomCustomer();
        Product product = TestProductFactory.createTestProduct(euros(1, 49));

        ThrowableAssert.ThrowingCallable invocation = () -> cart.addProduct(product, quantity);

        assertThatIllegalArgumentException().isThrownBy(invocation);
    }
}
