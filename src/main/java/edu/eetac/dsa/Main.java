package edu.eetac.dsa;

import edu.eetac.dsa.services.OrderService;
import edu.eetac.dsa.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import edu.eetac.dsa.services.OrderService;
import java.net.URI;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig()
                .register(ProductService.class)
                .register(OrderService.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws Exception {
        ProductManager pm = ProductManagerImpl.getInstance();
        pm.clear();

        pm.addProduct("P1", "Cafe", 1.20);
        pm.addProduct("P2", "Bocadillo", 3.50);
        pm.addProduct("P3", "Agua", 1.00);

        pm.addUser("U1", "Roger");

        HttpServer server = startServer();
        logger.info("Servidor REST arrancado en {}", BASE_URI);
        System.in.read();
        server.shutdownNow();
    }
}