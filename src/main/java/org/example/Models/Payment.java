package org.example.Models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Payment implements Document {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int paymentNumber;
    private LocalDateTime paymentDate; // дата и время
    private String paymentName; // Плательщик // тип оплаты и данные краты если онлайн *********7842

    public Payment(int id, int paymentNumber, LocalDateTime paymentDate, String paymentName) {
        this.id = id;
        this.paymentNumber = paymentNumber;
        this.paymentDate = paymentDate;
        this.paymentName = paymentName;
    }

    public Payment() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public DocumentType getType() {
        return DocumentType.PAYMENT;
    }

    @Override
    public List<String> displayInfo() {

        List<String> list = new ArrayList<>();
        list.add("id: "+ id);
        list.add("number: "+ paymentNumber);
        list.add("date: "+ paymentDate);
        list.add("clientName: "+ paymentName);

        return list;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String nameSupplier) {
        this.paymentName = nameSupplier;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public int getNumber() {
        return paymentNumber;
    }

    @Override
    public void setNumber(int paymentNumber) {
        this.paymentNumber = paymentNumber;
    }
}
