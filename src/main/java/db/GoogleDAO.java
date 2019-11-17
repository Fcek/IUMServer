package db;

import entities.GoogleEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class GoogleDAO extends AbstractDAO<GoogleEntity> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public GoogleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public GoogleEntity findById(Long id) {
        return get(id);
    }

    public GoogleEntity create(GoogleEntity googleEntity) {
        return persist(googleEntity);
    }

    public GoogleEntity update(GoogleEntity googleEntity) {
        return persist(googleEntity);
    }

    public void delete(GoogleEntity googleEntity) {
        currentSession().delete(googleEntity);
    }

}
