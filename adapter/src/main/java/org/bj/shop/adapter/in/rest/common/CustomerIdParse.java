package org.bj.shop.adapter.in.rest.common;

import jakarta.ws.rs.core.Response;
import org.bj.shop.model.customer.CustomerId;

import static org.bj.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

public final class CustomerIdParse {

    private CustomerIdParse() {}

    public static CustomerId parseCustomerId(String string) {
        try {
            return new CustomerId(Integer.parseInt(string));
        } catch (IllegalArgumentException e) {
            throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid customerId");
        }
    }
}
