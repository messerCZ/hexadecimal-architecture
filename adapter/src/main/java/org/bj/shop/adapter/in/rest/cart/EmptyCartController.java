package org.bj.shop.adapter.in.rest.cart;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bj.shop.application.port.in.cart.EmptyCartUseCase;
import org.bj.shop.model.customer.CustomerId;

import static org.bj.shop.adapter.in.rest.common.CustomerIdParse.parseCustomerId;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class EmptyCartController {

    private final EmptyCartUseCase emptyCartUseCase;

    public EmptyCartController(EmptyCartUseCase emptyCartUseCase) {
        this.emptyCartUseCase = emptyCartUseCase;
    }

    @DELETE
    @Path("/{customerId}")
    public void deleteCart(@PathParam("customerId") String customerIdString) {
        CustomerId customerId = parseCustomerId(customerIdString);
        emptyCartUseCase.emptyCart(customerId);
    }
}
