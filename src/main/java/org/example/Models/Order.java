package org.example.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order implements Document {
    private int id;
    private int orderNumber;
    private LocalDateTime orderDate;
    private String buyerName;
    private List<String> products;

    public Order() {
        products = new ArrayList<>();
    }

    public Order(int id, int orderNumber, LocalDateTime orderDate, String buyerName, List<String> products) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.buyerName = buyerName;
        this.products = products;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int  id) {
        this.id = id;
    }

    @Override
    public int getNumber() {
        return orderNumber;
    }

    @Override
    public void setNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public DocumentType getType() {
        return DocumentType.ORDER;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public List<String> getProducts() {
        if (products == null){
            products = new ArrayList<>();
        }

        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    @Override
    public List<String> displayInfo() {
//        String[] productInfo = getProducts().get(0).split(" ");

        List<String> list = new ArrayList<>();
        list.add("id: "+ getId());
        list.add("number: "+ getNumber());
        list.add("date: "+ getOrderDate());
        list.add("customerName: "+ getBuyerName());
//        list.add("Products: "+ productInfo[0] + " " + productInfo[1] + " " + productInfo[2] + " " + " (+" +  (getProducts().size() - 1) +")");

        return list;
    }
}

