package com.example.vag.repository;

import com.example.vag.model.Artwork;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class ArtworkRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ArtworkRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Artwork> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Artwork.class, id));
    }

    public List<Artwork> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Artwork", Artwork.class).list();
    }

    public List<Artwork> findByStatus(String status) {
        Session session = sessionFactory.getCurrentSession();
        Query<Artwork> query = session.createQuery("FROM Artwork WHERE status = :status", Artwork.class);
        query.setParameter("status", status);
        return query.list();
    }

    public List<Artwork> findByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Artwork> query = session.createQuery("FROM Artwork WHERE user.id = :userId", Artwork.class);
        query.setParameter("userId", userId);
        return query.list();
    }

    public List<Artwork> findByCategoryId(Long categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Artwork> query = session.createQuery("FROM Artwork WHERE category.id = :categoryId", Artwork.class);
        query.setParameter("categoryId", categoryId);
        return query.list();
    }

    public List<Artwork> findByExhibitionId(Long exhibitionId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Artwork> query = session.createQuery("FROM Artwork WHERE exhibition.id = :exhibitionId", Artwork.class);
        query.setParameter("exhibitionId", exhibitionId);
        return query.list();
    }

    public Artwork save(Artwork artwork) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(artwork);
        return artwork;
    }

    public void delete(Artwork artwork) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(artwork);
    }

    public List<Artwork> findApprovedArtworks(int page, int size) {
        Session session = sessionFactory.getCurrentSession();
        Query<Artwork> query = session.createQuery("FROM Artwork WHERE status = 'APPROVED' ORDER BY dateCreation DESC", Artwork.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.list();
    }

    public long countApprovedArtworks() {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Artwork WHERE status = 'APPROVED'", Long.class);
        return query.uniqueResult();
    }
    public Optional<Artwork> findByIdWithComments(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Artwork> query = session.createQuery(
                "SELECT a FROM Artwork a LEFT JOIN FETCH a.comments WHERE a.id = :id",
                Artwork.class
        );
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

}