package org.example.dao;

import org.example.Models.Order;
import org.example.Models.Payment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
        setEntities(getJdbcTemplate().query("SELECT * FROM payment", new BeanPropertyRowMapper<>(Payment.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, Payment payment) {
        String sql = "INSERT INTO payment (number, date, paymentname) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, payment.getNumber(), LocalDateTime.now(), payment.getPaymentName());
    }

    @Override
    public Payment findById(int id) {
        return getJdbcTemplate().query("SELECT * FROM payment WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Payment.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Payment findByNumber(int number) {
        return getJdbcTemplate().query("SELECT * FROM payment WHERE number=?", new Object[]{number}, new BeanPropertyRowMapper<>(Payment.class))
                .stream().findAny().orElse(null);
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