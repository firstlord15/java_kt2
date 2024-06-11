package org.example.dao;

import org.example.Models.Invoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDAO extends BaseDAO<Invoice> {
    private Invoice invoice;

    public InvoiceDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        invoice = new Invoice();
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
        setEntities(getJdbcTemplate().query("SELECT * FROM invoice", new BeanPropertyRowMapper<>(Invoice.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, Invoice invoice) {
        String sql = "INSERT INTO invoice (number, date, clientname, address) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, invoice.getNumber(), invoice.getInvoiceDate(), invoice.getClientName(), invoice.getAddress());
    }

    @Override
    public Invoice findById(int id) {
        return getJdbcTemplate().query("SELECT * FROM invoice WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Invoice.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Invoice findByNumber(int number) {
        return getJdbcTemplate().query("SELECT * FROM invoice WHERE number=?", new Object[]{number}, new BeanPropertyRowMapper<>(Invoice.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Invoice getDoc() {
        return invoice;
    }

    @Override
    public void setDoc(Invoice entity) {
        invoice = entity;
    }
}