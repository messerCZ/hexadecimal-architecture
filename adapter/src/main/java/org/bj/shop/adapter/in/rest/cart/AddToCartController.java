package org.bj.shop.adapter.in.rest.cart;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bj.shop.application.port.in.cart.AddToCartUseCase;
import org.bj.shop.application.port.in.cart.ProductNotFountException;
import org.bj.shop.model.cart.Cart;
import org.bj.shop.model.cart.NotEnoughItemsInStockException;
import org.bj.shop.model.customer.CustomerId;
import org.bj.shop.model.product.ProductId;

import static org.bj.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static org.bj.shop.adapter.in.rest.common.CustomerIdParse.parseCustomerId;
import static org.bj.shop.adapter.in.rest.common.ProductIdParser.parseProductId;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class AddToCartController {

    private final AddToCartUseCase addToCartUseCase;

    public AddToCartController(AddToCartUseCase addToCartUseCase) {
        this.addToCartUseCase = addToCartUseCase;
    }

    @POST
    @Path("/{customerId}/line-items")
    public CartWebModel addLineItem(
            @PathParam("customerId") String customerIdString,
            @QueryParam("productId") String productIdString,
            @QueryParam("quantity") int quantity) {
        CustomerId customerId = parseCustomerId(customerIdString);
        ProductId productId = parseProductId(productIdString);

        try {
            Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
            return CartWebModel.fromDomainModel(cart);
        } catch (ProductNotFountException e) {
            throw clientErrorException(Response.Status.BAD_REQUEST, "The requested product does not exist");
        } catch (NotEnoughItemsInStockException e) {
            throw clientErrorException(Response.Status.BAD_REQUEST, "Only %d items in stock".formatted(e.itemsInStock()));
        }
    }
}
