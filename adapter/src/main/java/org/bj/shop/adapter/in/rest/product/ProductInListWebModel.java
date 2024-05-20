package org.bj.shop.adapter.in.rest.product;

import org.bj.shop.model.money.Money;
import org.bj.shop.model.product.Product;

public record ProductInListWebModel(String id, String name, Money price, int itemsInStock) {

    public static ProductInListWebModel fromDomainModel(Product product) {
        return new ProductInListWebModel(product.id().value(), product.name(), product.price(), product.itemsInStock());
    }
}
