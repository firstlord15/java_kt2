package org.example.dao;

import org.example.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DocumentDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DocumentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<List<Document>> getDocList(){
        List<List<Document>> documents = new ArrayList<>();

        List<Invoice> invoices = jdbcTemplate.query("SELECT * FROM Invoice", new BeanPropertyRowMapper<>(Invoice.class));
        List<Order> orders = jdbcTemplate.query("SELECT * FROM public.order", new BeanPropertyRowMapper<>(Order.class));
        List<Payment> payments = jdbcTemplate.query("SELECT * FROM payment", new BeanPropertyRowMapper<>(Payment.class));
        List<PaymentInvoice> paymentInvoices = jdbcTemplate.query("SELECT * FROM paymentinvoice", new BeanPropertyRowMapper<>(PaymentInvoice.class));

        for (Invoice invoice : invoices) {
            List<Document> documentList = new ArrayList<>();
            int number = invoice.getNumber();
//            System.out.println("=========================================================");
//            System.out.println("invoiceId: " + invoice.getId());
//            System.out.println("invoiceNumber: " + number);

            documentList.add(orders.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null));
//            System.out.println("orderId: "+ Objects.requireNonNull(orders.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null)).getId());
//            System.out.println("orderNumber: "+ Objects.requireNonNull(orders.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null)).getNumber());

            documentList.add(payments.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null));
//            System.out.println("paymentsId: "+ Objects.requireNonNull(payments.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null)).getId());
//            System.out.println("paymentsNumber: "+ Objects.requireNonNull(payments.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null)).getNumber());

            documentList.add(paymentInvoices.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null));
//            System.out.println("paymentInvoicesId: "+ Objects.requireNonNull(paymentInvoices.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null)).getId());
//            System.out.println("paymentInvoicesNumber: "+ Objects.requireNonNull(paymentInvoices.stream().filter(x -> x.getNumber() == number).findFirst().orElse(null)).getNumber());

            documents.add(documentList);
        }
//        System.out.println("documents size: " + documents.size());
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

    public List<List<Document>> index() {
        return getDocList();
    }

    public List<Document> show(int id) {
        return getDocList(id);
    }
}
