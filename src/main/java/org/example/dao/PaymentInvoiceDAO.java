package org.example.dao;

import org.example.Models.Invoice;
import org.example.Models.PaymentInvoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        Field[] fields = PaymentInvoice.class.getDeclaredFields();

        result.add(PaymentInvoice.class.getSimpleName());
        for (Field f : fields) {
            result.add(f.getName());
        }

        return result;
    }
}