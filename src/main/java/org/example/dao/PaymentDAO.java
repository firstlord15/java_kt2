package org.example.dao;

import org.example.Models.Payment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentDAO extends BaseDAO<Payment> {
    private Payment payment;

    public PaymentDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        payment = new Payment();
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

    @Override
    public void save(JdbcTemplate jdbcTemplate, Payment payment) {
        String sql = "INSERT INTO payment (id, number, date, paymentname) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, payment.getNumber(), LocalDateTime.now(), payment.getPaymentName());
    }

    @Override
    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        Field[] fields = Payment.class.getDeclaredFields();

        result.add(Payment.class.getSimpleName());
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (i != 2) result.add(f.getName());
        }

        return result;
    }

    @Override
    public Payment getDoc() {
        return payment;
    }

    @Override
    public void setDoc(Payment entity) {
        payment = entity;
    }
}