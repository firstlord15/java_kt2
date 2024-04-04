package org.example.Models;

import java.time.LocalDateTime;

public class PaymentInvoice implements Document {
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String displayInfo() {
        return  "[Document PaymentInvoice]:" +
                "id: "+ id +"\n" +
                "number: "+ paymentInvoiceNumber +"\n" +
                "date: "+ paymentInvoiceDate +"\n" +
                "customerName: "+ customerName +"\n" +
                "comments: " + comments + "\n";
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
