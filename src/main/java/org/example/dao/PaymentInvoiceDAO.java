package org.example.dao;

import org.example.Models.PaymentInvoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
        setEntities(getJdbcTemplate().query("SELECT * FROM paymentinvoice ORDER BY id", new BeanPropertyRowMapper<>(PaymentInvoice.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, PaymentInvoice paymentInvoice) {
        String sql = "INSERT INTO paymentinvoice (number, date, customername, comments) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql, paymentInvoice.getNumber(), LocalDateTime.now(),
                paymentInvoice.getCustomerName(), paymentInvoice.getComments()
        );
    }

    @Override
    public void update(JdbcTemplate jdbcTemplate, PaymentInvoice entity, int id) {
        String sql = "UPDATE paymentinvoice SET number=?, date=?, customername=?, comments=? WHERE id=?";
        jdbcTemplate.update(
                sql, entity.getNumber(), entity.getInvoiceDate(), entity.getCustomerName(), entity.getComments(), id
        );
    }

    @Override
    public PaymentInvoice findById(int id) {
        return getJdbcTemplate().query("SELECT * FROM paymentinvoice WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(PaymentInvoice.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public PaymentInvoice findByNumber(int number) {
        return getJdbcTemplate().query("SELECT * FROM paymentinvoice WHERE number=?", new Object[]{number}, new BeanPropertyRowMapper<>(PaymentInvoice.class))
                .stream().findAny().orElse(null);
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