package edu.eetac.dsa.services;

import edu.eetac.dsa.ProductManager;
import edu.eetac.dsa.ProductManagerImpl;
import edu.eetac.dsa.models.Order;
import edu.eetac.dsa.models.Product;
import edu.eetac.dsa.models.dto.OrderItemRequest;
import edu.eetac.dsa.models.dto.OrderRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.PathParam;

import java.util.LinkedHashMap;
import java.util.Map;

@Path("/orders")
public class OrderService {

    private ProductManager pm;

    public OrderService() {
        this.pm = ProductManagerImpl.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersInfo() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("message", "La ruta /orders esta activa. Usa POST para crear pedidos.");
        payload.put("pendingOrders", pm.numOrders());
        payload.put("sampleRequest", """
                {
                  "userId": "U1",
                  "items": [
                    { "productId": "P1", "quantity": 2 },
                    { "productId": "P2", "quantity": 1 }
                  ]
                }
                """);
        return Response.ok(payload).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrder(OrderRequest request) {
        try {
            if (request == null || request.getUserId() == null || request.getItems() == null || request.getItems().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Pedido inválido")
                        .build();
            }

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
                    .entity("Error al crear el pedido: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/deliver")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deliverOrder() {
        try {
            Order delivered = pm.deliverOrder();

            return Response.ok(delivered).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No hay pedidos pendientes")
                    .build();
        }
    }
}
