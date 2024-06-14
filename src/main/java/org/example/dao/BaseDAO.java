package org.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public abstract class BaseDAO<T> {
    private final JdbcTemplate jdbcTemplate;
    private List<T> entities;
    private final Logger logger = Logger.getLogger(BaseDAO.class.getName());

    @Autowired
    public BaseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.entities = new ArrayList<>();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public List<T> getAll() {
        if (entities.isEmpty())
            upload();

        return entities;
    }

    public Optional<T> getById(int id) {
        if (entities.isEmpty())
            upload();

        return entities.stream().filter(entity -> getId(entity) == id).findFirst();
    }

    public Optional<T> getByNumber(int number) {
        if (entities.isEmpty())
            upload();

        return entities.stream().filter(entity -> getNumber(entity) == number).findFirst();
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public void clearEntities() {
        this.entities.clear();
    }

    public abstract T getDoc();
//    public abstract T getDocDB(JdbcTemplate jdbcTemplate, int locator);
    public abstract T findById(int locator);
    public abstract T findByNumber(int locator);
    public abstract void setDoc(T entity);
    public abstract int getId(T entity);
    public abstract int getNumber(T entity);
    public abstract void save(JdbcTemplate jdbcTemplate, T entity);
    public abstract void update(JdbcTemplate jdbcTemplate, T entity, int id);
    public abstract void upload();
}
