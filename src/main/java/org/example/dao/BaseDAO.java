package org.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class BaseDAO<T> {
    private final JdbcTemplate jdbcTemplate;
    private List<T> entities;

    @Autowired
    public BaseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.entities = new ArrayList<>();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public List<T> getAll() {
        if (entities.isEmpty()) { upload(); }
        return entities;
    }

    public T getById(int id) {
        T result = null;

        if (entities.isEmpty()) { upload(); }
        for (T entity : entities) {
            if (getId(entity) == id) {
                result = entity;
                break;
            }
        }

        return result;
    }

    public T getByNumber(int number) {
        T result = null;

        if (entities.isEmpty()) { upload(); }
        for (T entity : entities) {
            if (getNumber(entity) == number) {
                result = entity;
                break;
            }
        }

        return result;
    }

    public void set(List<T> entities) {
        this.entities = entities;
    }

    public abstract int getId(T entity);
    public abstract int getNumber(T entity);
    public abstract void upload();
}
