package org.example.dao;

import org.example.Models.Payment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentDAO extends BaseDAO<Payment> {
    public PaymentDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public int getId(Payment payment) {
        return payment.getId();
    }

    @Override
    public int getNumber(Payment payment) {
        return payment.getNumber();
    }

    @Override
    public void upload() {
        set(getJdbcTemplate().query("SELECT * FROM payment", new BeanPropertyRowMapper<>(Payment.class)));
    }
}