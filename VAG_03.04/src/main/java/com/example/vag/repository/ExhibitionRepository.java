package com.example.vag.repository;

import com.example.vag.model.Exhibition;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class ExhibitionRepository {

    private final SessionFactory sessionFactory;

    public ExhibitionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Exhibition> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Exhibition.class, id));
    }

    public List<Exhibition> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Exhibition", Exhibition.class).list();
    }

    public List<Exhibition> findByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Exhibition> query = session.createQuery("FROM Exhibition WHERE user.id = :userId", Exhibition.class);
        query.setParameter("userId", userId);
        return query.list();
    }

    public List<Exhibition> findPublicExhibitions() {
        Session session = sessionFactory.getCurrentSession();
        Query<Exhibition> query = session.createQuery("FROM Exhibition WHERE isPrivate = false", Exhibition.class);
        return query.list();
    }

    public Exhibition save(Exhibition exhibition) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(exhibition);
        return exhibition;
    }

    public void delete(Exhibition exhibition) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(exhibition);
    }

    public List<Exhibition> findPublicExhibitions(int page, int size) {
        Session session = sessionFactory.getCurrentSession();
        Query<Exhibition> query = session.createQuery("FROM Exhibition WHERE isPrivate = false ORDER BY id DESC", Exhibition.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.list();
    }

    public long countPublicExhibitions() {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Exhibition WHERE isPrivate = false", Long.class);
        return query.uniqueResult();
    }
}