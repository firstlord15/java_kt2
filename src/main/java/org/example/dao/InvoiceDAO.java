package org.example.dao;

import org.example.Models.Invoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceDAO extends BaseDAO<Invoice> {
    public InvoiceDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public int getId(Invoice invoice) {
        return invoice.getId();
    }

    @Override
    public int getNumber(Invoice invoice) {
        return invoice.getNumber();
    }

    @Override
    public void upload() {
        set(getJdbcTemplate().query("SELECT * FROM invoice", new BeanPropertyRowMapper<>(Invoice.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, Invoice invoice) {
        String sql = "INSERT INTO invoice (id, number, date, clientname, address) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, invoice.getId(), invoice.getNumber(), LocalDateTime.now(), invoice.getClientName(), invoice.getAddress());
    }

    @Override
    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        Field[] fields = Invoice.class.getDeclaredFields();

        result.add(Invoice.class.getSimpleName());
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (i != 2) result.add(f.getName());
        }

        return result;
    }
}