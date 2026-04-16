package edu.eetac.dsa.services;

import edu.eetac.dsa.ProductManager;
import edu.eetac.dsa.ProductManagerImpl;
import edu.eetac.dsa.models.Order;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
public class UserService {

    private ProductManager pm;

    public UserService() {
        this.pm = ProductManagerImpl.getInstance();
    }

    @GET
    @Path("/{id}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersByUser(@PathParam("id") String userId) {
        try {
            List<Order> orders = pm.getOrdersByUser(userId);
            return Response.ok(orders).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado")
                    .build();
        }
    }
}