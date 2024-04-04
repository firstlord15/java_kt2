package org.example.Models;

public enum DocumentType {
    ORDER("Order"),
    PAYMENT_INVOICE("PaymentInvoice"),
    PAYMENT("Payment"),
    INVOICE("Invoice");

    private final String className;

    DocumentType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
