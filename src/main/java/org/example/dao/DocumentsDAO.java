package org.example.dao;

import org.example.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentsDAO {
    private final JdbcTemplate jdbcTemplate;
    private final InvoiceDAO invoiceDAO;
    private final OrderDAO orderDAO;
    private final PaymentDAO paymentDAO;
    private final PaymentInvoiceDAO paymentInvoiceDAO;
    private final List<List<Document>> cachedDocuments;

    @Autowired
    public DocumentsDAO(JdbcTemplate jdbcTemplate, InvoiceDAO invoiceDAO, OrderDAO orderDAO, PaymentDAO paymentDAO, PaymentInvoiceDAO paymentInvoiceDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.invoiceDAO = invoiceDAO;
        this.orderDAO = orderDAO;
        this.paymentDAO = paymentDAO;
        this.paymentInvoiceDAO = paymentInvoiceDAO;
        this.cachedDocuments = new ArrayList<>();
    }

    private void clearCache() {
        cachedDocuments.clear();
        invoiceDAO.clearEntities();
        orderDAO.clearEntities();
        paymentDAO.clearEntities();
        paymentInvoiceDAO.clearEntities();
    }

    public List<List<Document>> getDocList() {
        if (cachedDocuments.isEmpty()) {
            List<Invoice> invoices = invoiceDAO.getAll();
            for (Invoice invoice : invoices) {
                List<Document> documentList = getDocumentsByNumber(invoice);
                documentList.add(invoice);
                cachedDocuments.add(documentList);
            }
        }
        return cachedDocuments;
    }

    public List<Document> getDocList(int id) {
        Invoice invoice = invoiceDAO.findById(id);
        return getDocumentsByNumber(invoice);
    }

    private List<Document> getDocumentsByNumber(Invoice invoice) {
        List<Document> documents = new ArrayList<>();
        documents.add(invoice);
        documents.add(orderDAO.findByNumber(invoice.getNumber()));
        documents.add(paymentDAO.findByNumber(invoice.getNumber()));
        documents.add(paymentInvoiceDAO.findByNumber(invoice.getNumber()));
        return documents;
    }

    public void save(Document[] documents) {
        invoiceDAO.save(jdbcTemplate, (Invoice) documents[0]);
        orderDAO.save(jdbcTemplate, (Order) documents[1]);
        paymentDAO.save(jdbcTemplate, (Payment) documents[2]);
        paymentInvoiceDAO.save(jdbcTemplate, (PaymentInvoice) documents[3]);
        clearCache();
    }

    public List<List<Document>> index() {
//        clearCache();
        return getDocList();
    }
    public List<Document> show(int id) {
        return getDocList(id);
    }
}
