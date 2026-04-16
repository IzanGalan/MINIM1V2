package edu.eetac.dsa.models;
import java.util.ArrayList;
import java.util.List;


public class User {
    private String id;
    private String name;
    private List<Order> servedOrders;

    public User(String id,String name){
        this.id = id;
        this.name = name;
        this.servedOrders = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Order> getServedOrders() {
        return servedOrders;
    }
    public void addServedOrder(Order order){
        this.servedOrders.add(order);
    }
}
