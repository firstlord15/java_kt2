package org.example.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Payment implements Document {
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

    @Override
    public List<String> displayInfo() {

        List<String> list = new ArrayList<>();
        list.add("id: "+ getId());
        list.add("number: "+ getNumber());
        list.add("date: "+ getPaymentDate());
        list.add("clientName: "+ getPaymentName());

        return list;
    }
}
