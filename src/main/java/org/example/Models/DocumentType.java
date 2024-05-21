package org.example.Models;

public enum DocumentType {
    ORDER("order"),
    PAYMENT_INVOICE("paymentInvoice"),
    PAYMENT("payment"),
    INVOICE("invoice");

    private final String className;

    DocumentType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
