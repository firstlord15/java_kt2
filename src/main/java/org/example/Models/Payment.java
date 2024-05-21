package org.example.Models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

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
    public void save(JdbcTemplate jdbcTemplate) {
        String sql = "INSERT INTO payment (id, number, date, paymentname) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, paymentNumber, paymentDate, paymentName);
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
