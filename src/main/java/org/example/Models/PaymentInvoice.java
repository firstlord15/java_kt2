package org.example.Models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class PaymentInvoice implements Document {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int paymentInvoiceNumber;
    private LocalDateTime paymentInvoiceDate;
    private String customerName;
    private String comments;

    public PaymentInvoice(int id, int paymentInvoiceNumber, LocalDateTime paymentInvoiceDate, String customerName, String comments) {
        this.id = id;
        this.paymentInvoiceNumber = paymentInvoiceNumber;
        this.paymentInvoiceDate = paymentInvoiceDate;
        this.customerName = customerName;
        this.comments = comments;
    }

    public PaymentInvoice() {

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
        return DocumentType.PAYMENT_INVOICE;
    }

    @Override
    public List<String> displayInfo() {
        List<String> list = new ArrayList<>();
        list.add("id: "+ id);
        list.add("number: "+ paymentInvoiceNumber);
        list.add("date: "+ paymentInvoiceDate);
        list.add("clientName: "+ customerName);
        list.add("comments: "+ comments);

        return list;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getInvoiceDate() {
        return paymentInvoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.paymentInvoiceDate = invoiceDate;
    }

    @Override
    public int getNumber() {
        return paymentInvoiceNumber;
    }

    @Override
    public void setNumber(int paymentInvoiceNumber) {
        this.paymentInvoiceNumber = paymentInvoiceNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
