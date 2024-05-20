package org.bj.shop.model.product;

import org.bj.shop.model.money.Money;

public class TestProductFactory {

    private static final int ENOUGHT_ITEMS_IN_STOCK = Integer.MAX_VALUE;

    public static Product createTestProduct(Money price) {
        return createTestProduct(price, ENOUGHT_ITEMS_IN_STOCK);
    }

    public static Product createTestProduct(Money price, int itemsInStock) {
        return new Product(
                ProductId.randomProductId(),
                "any name",
                "any description",
                price,
                itemsInStock);
    }
}
