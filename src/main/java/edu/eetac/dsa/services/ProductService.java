package edu.eetac.dsa.services;

import edu.eetac.dsa.ProductManager;
import edu.eetac.dsa.ProductManagerImpl;
import edu.eetac.dsa.models.Product;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/products")
public class ProductService {

    private ProductManager pm;

    public ProductService() {
        this.pm = ProductManagerImpl.getInstance();
    }

    @GET
    @Path("/price")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsByPrice() {
        return this.pm.getProductsByPrice();
    }

    @GET
    @Path("/sales")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsBySales() {
        return this.pm.getProductsBySales();
    }


}