package org.evil.service;

import org.evil.domain.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/shop")
public class JerseyRest {
    //store Supermarket assortment
    private static Supermarket[] supermarketData = {
            new Supermarket("0001", "Bakery products", "White bread", "Bread straight from the factory!",50),
            new Supermarket("0002","Chemistry products", "Cleaning agent", "Remove any dirt",450),
            new Supermarket("0003", "Technics", "A vacuum cleaner", "Make your house clean",8000),
            new Supermarket("0004", "Water products", "Coca-Cola", "Refreshing drink",100),
            new Supermarket("0005", "Bakery products", "Long loaf", "Bread straight from the factory!",60)
    };
    // the number of products in the store
    private static int[] quantityData = {0, 2, 3, 5, 1};
    private static StoreAssortment storeAssortment = new StoreAssortment(supermarketData, quantityData);

    /**
     * // EXAMPLE POST REQUEST
     *
     * POST http://localhost:8081/RestExample/rest/shop/payment
     * Accept: application/json
     * Content-Type: application/json
     *
     * {
     *   "productId": "123",
     *   "quantity": 2
     * }
     *
     */

    @POST
    @Path("/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buySupermarket(UserOrder userOrder) {
        int size = storeAssortment.getSupermarkets().length;
        for (int i = 0; i < size; i++) {
            Supermarket currentSupermarket = storeAssortment.getSupermarkets()[i];
            if (currentSupermarket.getId().equals(userOrder.getProductId())) {
                double orderPrice = currentSupermarket.getPrice() * userOrder.getQuantity();
                PaymentResult goodResult = new PaymentResult(
                        currentSupermarket.toString(),
                        userOrder.getQuantity(),
                        orderPrice,
                        "Buy successful!");
                return Response.ok(goodResult).build();
            }
        }
        PaymentResult badResult = new PaymentResult(
                userOrder.getProductId(),
                userOrder.getQuantity(),
                0,
                "Buy failure! Model with id = " + userOrder.getProductId() + " not found");
        return Response.ok(badResult).build();
    }

/*
    @GET
    @Path("/assortment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoreCatalog() {
        return Response.ok(storeAssortment).build();
    }

 */
}
