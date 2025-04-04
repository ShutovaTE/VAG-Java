package com.example.vag.repository;

import com.example.vag.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class CategoryRepository {

    private final SessionFactory sessionFactory;

    public CategoryRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Category> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Category.class, id));
    }

    public List<Category> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Category", Category.class).list();
    }

    public Category save(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(category);
        return category;
    }

    public void delete(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(category);
    }
}