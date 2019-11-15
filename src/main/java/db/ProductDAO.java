package db;

import entities.AccountEntity;
import entities.ProductEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class ProductDAO extends AbstractDAO<ProductEntity> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public ProductEntity findById(Long id) {
        return get(id);
    }

    public ProductEntity create(ProductEntity productEntity) {
        return persist(productEntity);
    }

    public ProductEntity update(ProductEntity productEntity) {
        return persist(productEntity);
    }

    public void delete(ProductEntity productEntity) {
        currentSession().delete(productEntity);
    }
}
