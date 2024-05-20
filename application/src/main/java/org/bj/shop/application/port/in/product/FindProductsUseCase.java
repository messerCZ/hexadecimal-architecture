package org.bj.shop.application.port.in.product;

import org.bj.shop.model.product.Product;

import java.util.List;

public interface FindProductsUseCase {

    List<Product> findByNameOrDescription(String query);
}
