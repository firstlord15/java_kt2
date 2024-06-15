package org.example.dao;

import org.example.Models.Invoice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
        setEntities(getJdbcTemplate().query("SELECT * FROM invoice ORDER BY id", new BeanPropertyRowMapper<>(Invoice.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, Invoice invoice) {
        String sql = "INSERT INTO invoice (number, date, clientname, address) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, invoice.getNumber(), invoice.getDate(), invoice.getClientName(), invoice.getAddress());
    }

    @Override
    public void update(JdbcTemplate jdbcTemplate, Invoice entity, int id) {
        String sql = "UPDATE invoice SET number=?, date=?, clientname=?, address=? WHERE id=?";
        jdbcTemplate.update(sql, entity.getNumber(), entity.getDate(), entity.getClientName(), entity.getAddress(), id);
    }

    private Invoice mapRowToInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getInt("id"));
        invoice.setNumber(rs.getInt("number"));
        invoice.setClientName(rs.getString("clientName"));
        invoice.setAddress(rs.getString("address"));
        Timestamp timestamp = rs.getTimestamp("date");
        if (timestamp != null) {
            invoice.setDate(timestamp.toLocalDateTime());
        }
        return invoice;
    }

    @Override
    public Invoice findById(int id) {
        return getJdbcTemplate().query(
                "SELECT * FROM invoice WHERE id = ?", new Object[]{id}, (rs, rowNum) -> mapRowToInvoice(rs)
        ).stream().findAny().orElse(null);
    }

    @Override
    public Invoice findByNumber(int number) {
        return getJdbcTemplate().query(
                "SELECT * FROM invoice WHERE number = ?", new Object[]{number}, (rs, rowNum) -> mapRowToInvoice(rs)
        ).stream().findAny().orElse(null);
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