package org.bj.shop.adapter.in.rest.cart;

import org.bj.shop.model.cart.CartLineItem;
import org.bj.shop.model.money.Money;
import org.bj.shop.model.product.Product;

public record CartLineItemWebModel(String productId, String productName, Money price, int quantity) {

    public static CartLineItemWebModel fromDomainModel(CartLineItem lineItem) {
        Product product = lineItem.product();
        return new CartLineItemWebModel(
                product.id().value(), product.name(), product.price(), lineItem.quantity()
        );
    }
}
