package org.example.Models;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentInvoice implements Document {
    private int id;
    private int paymentInvoiceNumber;
    private LocalDateTime paymentInvoiceDate;
    private String customerName;
    private String comments;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getDate() {
        return paymentInvoiceDate;
    }

    public void setDate(LocalDateTime invoiceDate) {
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

    @Override
    public List<String> displayInfo() {
        List<String> list = new ArrayList<>();
        list.add("id: "+ getId());
        list.add("number: "+ getNumber());
        list.add("date: "+ getDate().format(formatter));
        list.add("customerName: "+ getCustomerName());
        list.add("comments: "+ getComments());

        return list;
    }
}
