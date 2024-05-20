package org.bj.shop.model.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.bj.shop.model.customer.CustomerId;
import org.bj.shop.model.money.Money;
import org.bj.shop.model.product.Product;
import org.bj.shop.model.product.ProductId;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Accessors(fluent = true)
@RequiredArgsConstructor
public class Cart {

    @Getter private final CustomerId customerId;

    private final Map<ProductId, CartLineItem> lineItemMap = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) throws NotEnoughItemsInStockException {
        lineItemMap
                .computeIfAbsent(product.id(), ignored -> new CartLineItem(product))
                .increaseQuantityBy(quantity, product.itemsInStock());
    }

    public List<CartLineItem> lineItems() {
        return List.copyOf(lineItemMap.values());
    }

    public int numberOfItems() {
        return lineItemMap.values().stream().mapToInt(CartLineItem::quantity).sum();
    }

    public Money subTotal() {
        return lineItemMap.values().stream()
                .map(CartLineItem::subTotal)
                .reduce(Money::add)
                .orElse(null);
    }
}
