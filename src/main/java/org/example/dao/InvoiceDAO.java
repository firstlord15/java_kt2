package org.example.dao;

import org.example.Models.Invoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}