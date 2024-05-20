package org.example.dao;

import org.example.Models.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderDAO extends BaseDAO<Order> {
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public int getId(Order order) {
        return order.getId();
    }

    @Override
    public int getNumber(Order order) {
        return order.getNumber();
    }

    @Override
    public void upload() {
        set(getJdbcTemplate().query("SELECT * FROM \"order\"", new BeanPropertyRowMapper<>(Order.class)));
    }
}