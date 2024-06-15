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
        return getDocumentsByNumber(invoiceDAO.findById(id));
    }

    private List<Document> getDocumentsByNumber(Invoice invoice) {
        List<Document> documents = new ArrayList<>();
        documents.add(invoice);
        documents.add(orderDAO.findByNumber(invoice.getNumber()));
        documents.add(paymentDAO.findByNumber(invoice.getNumber()));
        documents.add(paymentInvoiceDAO.findByNumber(invoice.getNumber()));
        return documents;
    }

    public Document getDocById(int id, DocumentType documentType) {
        switch (documentType) {
            case INVOICE: return invoiceDAO.findById(id);
            case ORDER: return orderDAO.findById(id);
            case PAYMENT: return paymentDAO.findById(id);
            case PAYMENT_INVOICE: return paymentInvoiceDAO.findById(id);
            default:
                throw new IllegalStateException("Unexpected value: " + documentType);
        }
    }

    public void save(Document[] documents) {
        invoiceDAO.save(jdbcTemplate, (Invoice) documents[0]);
        orderDAO.save(jdbcTemplate, (Order) documents[1]);
        paymentDAO.save(jdbcTemplate, (Payment) documents[2]);
        paymentInvoiceDAO.save(jdbcTemplate, (PaymentInvoice) documents[3]);
        clearCache();
    }

    public void update(int id, Document[] documents) {
        invoiceDAO.update(jdbcTemplate, (Invoice) documents[0], id);
        orderDAO.update(jdbcTemplate, (Order) documents[1], id);
        paymentDAO.update(jdbcTemplate, (Payment) documents[2], id);
        paymentInvoiceDAO.update(jdbcTemplate, (PaymentInvoice) documents[3], id);
        clearCache();
    }

    public boolean existsByNumber(int number) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM invoice WHERE number = ?",
                new Object[]{number},
                Integer.class
        );
        return count != null && count > 0;
    }

    public List<List<Document>> index() {
        return getDocList();
    }

    public List<Document> show(int id) {
        return getDocList(id);
    }
}
