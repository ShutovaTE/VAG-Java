package com.example.vag.repository;

import com.example.vag.model.Artwork;
import com.example.vag.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CommentRepository {

    private final SessionFactory sessionFactory;

    public CommentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public List<Comment> findByArtwork(Artwork artwork) {
        Session session = sessionFactory.getCurrentSession();
        Query<Comment> query = session.createQuery("FROM Comment WHERE artwork = :artwork ORDER BY dateCreated DESC", Comment.class);
        query.setParameter("artwork", artwork);
        return query.list();
    }

    public void save(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(comment);
    }

    public void delete(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(comment);
    }

}
