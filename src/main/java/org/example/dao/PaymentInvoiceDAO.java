package org.example.dao;

import org.example.Models.PaymentInvoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentInvoiceDAO extends BaseDAO<PaymentInvoice> {
    public PaymentInvoiceDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
}