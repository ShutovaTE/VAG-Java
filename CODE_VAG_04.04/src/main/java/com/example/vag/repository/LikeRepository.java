package com.example.vag.repository;

import com.example.vag.model.Artwork;
import com.example.vag.model.Like;
import com.example.vag.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class LikeRepository {

    private final SessionFactory sessionFactory;


    public LikeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public boolean existsByArtworkAndUser(Artwork artwork, User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Like l WHERE l.artwork = :artwork AND l.user = :user",
                Long.class
        );
        query.setParameter("artwork", artwork);
        query.setParameter("user", user);
        return query.getSingleResult() > 0;
    }
    public Optional<Like> findByArtworkAndUser(Artwork artwork, User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<Like> query = session.createQuery("FROM Like WHERE artwork = :artwork AND user = :user", Like.class);
        query.setParameter("artwork", artwork);
        query.setParameter("user", user);
        return query.uniqueResultOptional();
    }

    public void save(Like like) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(like);
    }

    public void delete(Like like) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(like);
    }

    public List<Like> findByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<Like> query = session.createQuery("FROM Like WHERE user = :user", Like.class);
        query.setParameter("user", user);
        return query.list();
    }
}