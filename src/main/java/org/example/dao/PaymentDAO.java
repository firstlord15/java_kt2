package org.example.dao;

import org.example.Models.Invoice;
import org.example.Models.Payment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        Field[] fields = Payment.class.getDeclaredFields();

        result.add(Payment.class.getSimpleName());
        for (Field f : fields) {
            result.add(f.getName());
        }

        return result;
    }
}