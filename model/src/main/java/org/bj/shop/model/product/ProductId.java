package org.bj.shop.model.product;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public record ProductId(String value) {

    private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int LENGTH_OF_NEW_PRODUCT_IDS = 8;

    public ProductId {
        Objects.requireNonNull(value, "Product ID must not be null");
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Product ID must not be null or blank");
        }
    }

    public static ProductId randomProductId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] chars = new char[LENGTH_OF_NEW_PRODUCT_IDS];
        for (int i = 0; i < LENGTH_OF_NEW_PRODUCT_IDS; i++) {
            chars[i] = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
        }

        return new ProductId(new String(chars));
    }
}
