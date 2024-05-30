package org.example.dao;

import org.example.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DocumentsDAO {
    private final JdbcTemplate jdbcTemplate;
    private final InvoiceDAO invoiceDAO;
    private final OrderDAO orderDAO;
    private final PaymentDAO paymentDAO;
    private final PaymentInvoiceDAO paymentInvoiceDAO;

    @Autowired
    public DocumentsDAO(JdbcTemplate jdbcTemplate, InvoiceDAO invoiceDAO, OrderDAO orderDAO, PaymentDAO paymentDAO, PaymentInvoiceDAO paymentInvoiceDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.invoiceDAO = invoiceDAO;
        this.orderDAO = orderDAO;
        this.paymentDAO = paymentDAO;
        this.paymentInvoiceDAO = paymentInvoiceDAO;
    }

    public List<List<String>> getAllFields() {
        List<List<String>> fields = new ArrayList<>();
        fields.add(invoiceDAO.getFields());
        fields.add(orderDAO.getFields());
        fields.add(paymentDAO.getFields());
        fields.add(paymentInvoiceDAO.getFields());

        return fields;
    }

    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        documents.add(invoiceDAO.getDoc());
        documents.add(orderDAO.getDoc());
        documents.add(paymentDAO.getDoc());
        documents.add(paymentInvoiceDAO.getDoc());

        return documents;
    }

    public List<List<Document>> getDocList(){
        List<List<Document>> documents = new ArrayList<>();

        List<Invoice> invoices = invoiceDAO.getAll();

        for (Invoice invoice : invoices) {
            List<Document> documentList = new ArrayList<>();
            int number = invoice.getNumber();
            documentList.add(orderDAO.getByNumber(number));
            documentList.add(paymentDAO.getByNumber(number));
            documentList.add(paymentInvoiceDAO.getByNumber(number));
            documents.add(documentList);
        }
        return documents;
    }

    public List<Document> getDocList(int id){
        List<Document> documents = new ArrayList<>();

        Invoice invoice = jdbcTemplate
                .query("SELECT * FROM Invoice WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Invoice.class))
                .stream().findAny().orElse(null);

        int number = Objects.requireNonNull(invoice).getNumber();
        Order order = jdbcTemplate
                .query("SELECT * FROM \"order\" WHERE number=?", new Object[]{number}, new BeanPropertyRowMapper<>(Order.class))
                .stream().findAny().orElse(null);

        Payment payment = jdbcTemplate
                .query("SELECT * FROM payment WHERE number=?", new Object[]{number}, new BeanPropertyRowMapper<>(Payment.class))
                .stream().findAny().orElse(null);

        PaymentInvoice paymentInvoice = jdbcTemplate
                .query("SELECT * FROM paymentinvoice WHERE number=?", new Object[]{number}, new BeanPropertyRowMapper<>(PaymentInvoice.class))
                .stream().findAny().orElse(null);

        documents.add(invoice);
        documents.add(order);
        documents.add(payment);
        documents.add(paymentInvoice);
        return documents;
    }

    public void save(List<Document> document){
        invoiceDAO.save(jdbcTemplate, (Invoice) document.get(0));
        orderDAO.save(jdbcTemplate, (Order) document.get(1));
        paymentDAO.save(jdbcTemplate, (Payment) document.get(2));
        paymentInvoiceDAO.save(jdbcTemplate, (PaymentInvoice) document.get(3));
    }

    public List<List<Document>> index() {
        return getDocList();
    }
    public List<Document> show(int id) {
        return getDocList(id);
    }
}
