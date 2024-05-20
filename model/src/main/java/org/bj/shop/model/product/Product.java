package org.bj.shop.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bj.shop.model.money.Money;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class Product {

    private final ProductId id;
    private final String name;
    private final String description;
    private final Money price;
    private int itemsInStock;
}
