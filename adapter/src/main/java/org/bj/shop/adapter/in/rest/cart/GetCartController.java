package org.bj.shop.adapter.in.rest.cart;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bj.shop.application.port.in.cart.GetCartUseCase;
import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.customer.CustomerId;

import static org.bj.shop.adapter.in.rest.common.CustomerIdParse.parseCustomerId;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class GetCartController {

    private final GetCartUseCase getCartUseCase;

    public GetCartController(GetCartUseCase getCartUseCase) {
        this.getCartUseCase = getCartUseCase;
    }

    @GET
    @Path("/{customerId}")
    public CartWebModel getCart(@PathParam("customerId") String customerIdString) {
        CustomerId customerId = parseCustomerId(customerIdString);
        Cart cart = getCartUseCase.getCart(customerId);
        return CartWebModel.fromDomainModel(cart);
    }
}
