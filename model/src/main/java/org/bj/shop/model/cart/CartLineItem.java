package org.bj.shop.model.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.bj.shop.model.money.Money;
import org.bj.shop.model.product.Product;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class CartLineItem {

    private final Product product;
    private int quantity;

    public void increaseQuantityBy(int augent, int itemsInStock) throws NotEnoughItemsInStockException {
        if (augent < 1) {
            throw new IllegalArgumentException("You must add at least one item");
        }

        int newQuantity = quantity + augent;
        if (newQuantity > itemsInStock) {
            throw new NotEnoughItemsInStockException("Product %s has less items in stock (%d) than the requested total quantity (%d)"
                    .formatted(product.id(), product.itemsInStock(), newQuantity),
                    product.itemsInStock());
        }

        this.quantity = newQuantity;
    }

    public Money subTotal() {
        return product.price().multiply(quantity);
    }


}
