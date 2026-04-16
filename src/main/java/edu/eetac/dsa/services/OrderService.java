package edu.eetac.dsa.services;

import edu.eetac.dsa.ProductManager;
import edu.eetac.dsa.ProductManagerImpl;
import edu.eetac.dsa.models.Order;
import edu.eetac.dsa.models.Product;
import edu.eetac.dsa.models.dto.OrderItemRequest;
import edu.eetac.dsa.models.dto.OrderRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/orders")
public class OrderService {

    private ProductManager pm;

    public OrderService() {
        this.pm = ProductManagerImpl.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrder(OrderRequest request) {
        try {
            Order order = new Order(request.getUserId());

            for (OrderItemRequest itemRequest : request.getItems()) {
                Product product = pm.getProduct(itemRequest.getProductId());

                if (product == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Producto no encontrado: " + itemRequest.getProductId())
                            .build();
                }

                order.addItem(product, itemRequest.getQuantity());
            }

            pm.addOrder(order);

            return Response.status(Response.Status.CREATED)
                    .entity("Pedido creado correctamente")
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al crear el pedido")
                    .build();
        }
    }
}