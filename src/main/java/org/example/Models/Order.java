
package org.example.Models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Order implements Document {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int orderNumber;
    private LocalDateTime orderDate;
    private String buyerName;
    private ArrayList<String> products;

    public Order(int id, int orderNumber, LocalDateTime orderDate, String buyerName, ArrayList<String> products) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.buyerName = buyerName;
        this.products = products;
    }

    public Order() {

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
    public List<String> displayInfo() {
        String[] productInfo = products.get(0).split(" ");

        List<String> list = new ArrayList<>();
        list.add("id: "+ id);
        list.add("number: "+ orderNumber);
        list.add("date: "+ orderDate);
        list.add("customerName: "+ buyerName);
        list.add("Products: "+ productInfo[0] + " " + productInfo[1] + " " + productInfo[2] + " " + " (+" +  (products.size() - 1) +")");

        return list;
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

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }
}

