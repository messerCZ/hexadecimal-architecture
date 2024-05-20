package org.bj.shop.model.customer;


public record CustomerId(int value) {

    public CustomerId {
        if (value < 1) {
            throw new IllegalArgumentException("Customer ID must be greater than 0");
        }
    }

}
