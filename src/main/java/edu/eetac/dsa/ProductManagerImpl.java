package edu.eetac.dsa;

import edu.eetac.dsa.models.Order;
import edu.eetac.dsa.models.OrderItem;
import edu.eetac.dsa.models.Product;
import edu.eetac.dsa.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ProductManagerImpl implements ProductManager {

    private static final Logger logger = LogManager.getLogger(ProductManagerImpl.class);
    private static ProductManager instance;

    private List<Product> products;
    private List<Order> orders;
    private HashMap<String, User> users;

    private ProductManagerImpl() {
        this.products = new ArrayList<>();
        this.orders = new LinkedList<>();
        this.users = new HashMap<>();
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManagerImpl();
        }
        return instance;
    }

    @Override
    public void addProduct(String id, String name, double price) {
        logger.info("Inicio addProduct(id={}, name={}, price={})", id, name, price);

        Product p = new Product(id, name, price);
        this.products.add(p);

        logger.info("Fin addProduct(). Producto añadido={}", p.getName());
    }

    @Override
    public List<Product> getProductsByPrice() {
        logger.info("Inicio getProductsByPrice()");

        List<Product> lp = new ArrayList<>(this.products);
        lp.sort(Comparator.comparingDouble(Product::getPrice));

        logger.info("Fin getProductsByPrice(). Num productos={}", lp.size());
        return lp;
    }

    @Override
    public void addOrder(Order order) {
        logger.info("Inicio addOrder(orderId={}, userId={})", order.getId(), order.getUserId());

        User user = this.users.get(order.getUserId());
        if (user == null) {
            logger.error("Usuario no encontrado con id={}", order.getUserId());
            throw new RuntimeException("User not found");
        }

        this.orders.add(order);

        logger.info("Fin addOrder(). Pedidos pendientes={}", this.orders.size());
    }

    @Override
    public int numOrders() {
        logger.info("Inicio numOrders()");
        int n = this.orders.size();
        logger.info("Fin numOrders(). Resultado={}", n);
        return n;
    }

    @Override
    public Order deliverOrder() {
        logger.info("Inicio deliverOrder()");

        if (this.orders.isEmpty()) {
            logger.error("No hay pedidos pendientes");
            throw new RuntimeException("No pending orders");
        }

        Order o = this.orders.remove(0);
        o.markServed();

        for (OrderItem item : o.getItems()) {
            item.getProduct().addSales(item.getQuantity());
        }

        User u = this.users.get(o.getUserId());
        if (u == null) {
            logger.fatal("Inconsistencia: usuario {} no existe al entregar pedido {}", o.getUserId(), o.getId());
            throw new RuntimeException("Fatal error: user not found");
        }

        u.addServedOrder(o);

        logger.info("Fin deliverOrder(). Pedido entregado={}, pedidos pendientes={}", o.getId(), this.orders.size());
        return o;
    }

    @Override
    public Product getProduct(String productId) {
        logger.info("Inicio getProduct(productId={})", productId);

        for (Product p : this.products) {
            if (p.getId().equals(productId)) {
                logger.info("Fin getProduct(). Producto encontrado={}", p.getName());
                return p;
            }
        }

        logger.error("Producto no encontrado con id={}", productId);
        return null;
    }

    @Override
    public User getUser(String userId) {
        logger.info("Inicio getUser(userId={})", userId);

        User u = this.users.get(userId);

        if (u == null) {
            logger.error("Usuario no encontrado con id={}", userId);
        } else {
            logger.info("Fin getUser(). Usuario encontrado={}", u.getName());
        }

        return u;
    }

    @Override
    public List<Order> getOrdersByUser(String userId) {
        logger.info("Inicio getOrdersByUser(userId={})", userId);

        User u = this.users.get(userId);
        if (u == null) {
            logger.error("Usuario no encontrado con id={}", userId);
            throw new RuntimeException("User not found");
        }

        logger.info("Fin getOrdersByUser(). Num pedidos servidos={}", u.getServedOrders().size());
        return u.getServedOrders();
    }

    @Override
    public List<Product> getProductsBySales() {
        logger.info("Inicio getProductsBySales()");

        List<Product> lp = new ArrayList<>(this.products);
        lp.sort((p1, p2) -> Integer.compare(p2.getSales(), p1.getSales()));

        logger.info("Fin getProductsBySales(). Num productos={}", lp.size());
        return lp;
    }

    @Override
    public void addUser(String id, String name) {
        logger.info("Inicio addUser(id={}, name={})", id, name);

        User u = new User(id, name);
        this.users.put(id, u);

        logger.info("Fin addUser(). Usuario añadido={}", name);
    }

    @Override
    public void clear() {
        logger.info("Inicio clear()");
        this.products.clear();
        this.orders.clear();
        this.users.clear();
        logger.info("Fin clear()");
    }
}