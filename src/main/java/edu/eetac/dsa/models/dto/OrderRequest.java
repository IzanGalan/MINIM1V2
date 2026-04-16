package edu.eetac.dsa.models.dto;

import java.util.List;

public class OrderRequest {
    private String userId;
    private List<OrderItemRequest> items;

    public OrderRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}