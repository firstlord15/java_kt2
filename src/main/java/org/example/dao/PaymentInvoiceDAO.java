package org.example.dao;

import org.example.Models.PaymentInvoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentInvoiceDAO extends BaseDAO<PaymentInvoice> {
    private PaymentInvoice paymentInvoice;

    public PaymentInvoiceDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.paymentInvoice = new PaymentInvoice();
    }

    @Override
    public int getId(PaymentInvoice paymentInvoice) {
        return paymentInvoice.getId();
    }

    @Override
    public int getNumber(PaymentInvoice paymentInvoice) {
        return paymentInvoice.getNumber();
    }

    @Override
    public void upload() {
        set(getJdbcTemplate().query("SELECT * FROM paymentinvoice", new BeanPropertyRowMapper<>(PaymentInvoice.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, PaymentInvoice paymentInvoice) {
        String sql = "INSERT INTO paymentinvoice (id, number, date, customername, comments) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql, paymentInvoice.getNumber(), LocalDateTime.now(),
                paymentInvoice.getCustomerName(), paymentInvoice.getComments()
        );
    }

    @Override
    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        Field[] fields = PaymentInvoice.class.getDeclaredFields();

        result.add(PaymentInvoice.class.getSimpleName());
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (i != 2) result.add(f.getName());
        }

        return result;
    }

    @Override
    public PaymentInvoice getDoc() {
        return paymentInvoice;
    }

    @Override
    public void setDoc(PaymentInvoice entity) {
        paymentInvoice = entity;
    }
}