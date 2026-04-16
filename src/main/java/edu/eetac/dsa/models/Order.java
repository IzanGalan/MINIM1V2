package edu.eetac.dsa.models;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private String id;
    private String userId;
    private List<OrderItem> items;
    private boolean served;

    public Order(String userId){
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.items = new ArrayList<>();
        this.served = false;
    }
    public String getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public List<OrderItem> getItems() {
        return items;
    }
    public boolean isServed() {
        return served;
    }
    public void addItem(Product product, int quantity) {
        this.items.add(new OrderItem(product, quantity));
    }
    public void markServed() {
        this.served = true;
    }

}
