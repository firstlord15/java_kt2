package org.example.Models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;


public class Invoice implements Document {
    private int id;
    private int invoiceNumber;
    private LocalDateTime invoiceDate; // дата и время
    private String clientName; // заказчик
    private String address;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public Invoice() {

    }

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

    public LocalDateTime getDate() {
        return invoiceDate;
    }

    public void setDate(LocalDateTime invoiceDate) {
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

    @Override
    public List<String> displayInfo() {
        List<String> list = new ArrayList<>();
        list.add("id: "+ getId());
        list.add("number: "+ getNumber());
        list.add("date: "+ getDate().format(formatter));
        list.add("clientName: "+ getClientName());
        list.add("address: "+ getAddress());

        return list;
    }
}
