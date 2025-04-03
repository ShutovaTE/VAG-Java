package com.example.vag.repository;

import com.example.vag.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public class RoleRepository {

    private final SessionFactory sessionFactory;

    public RoleRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Role> findByName(Role.RoleName name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery("FROM Role WHERE name = :name", Role.class);
        query.setParameter("name", name);
        return query.uniqueResultOptional();
    }

    public void save(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(role);
    }
}