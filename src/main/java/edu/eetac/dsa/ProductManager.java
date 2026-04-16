package edu.eetac.dsa;

import edu.eetac.dsa.models.Order;
import edu.eetac.dsa.models.Product;
import edu.eetac.dsa.models.User;

import java.util.List;

public interface ProductManager {

    void addProduct(String id, String name, double price);

    List<Product> getProductsByPrice();

    void addOrder(Order order);

    int numOrders();

    Order deliverOrder();

    Product getProduct(String productId);

    User getUser(String userId);

    List<Order> getOrdersByUser(String userId);

    List<Product> getProductsBySales();

    void addUser(String id, String name);

    void clear();
}