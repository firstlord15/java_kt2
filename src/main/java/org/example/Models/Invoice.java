package org.example.Models;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

import java.time.LocalDateTime;


//@Entity
public class Invoice implements Document {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int invoiceNumber;
    private LocalDateTime invoiceDate; // дата и время
    private String clientName; // заказчик
    private String address;

    public Invoice() {}

    public Invoice(int id, int invoiceNumber, LocalDateTime invoiceDate, String clientName, String address) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.clientName = clientName;
        this.address = address;
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
        return  getType() + ":" +
                "id: "+ id +"\n" +
                "number: "+ invoiceNumber +"\n" +
                "date: "+ invoiceDate +"\n" +
                "clientName: "+ clientName +"\n" +
                "address: "+ address +"\n";
    }

    @Override
    public int getNumber() {
        return invoiceNumber;
    }

    @Override
    public void setNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public DocumentType getType() {
        return DocumentType.INVOICE;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
