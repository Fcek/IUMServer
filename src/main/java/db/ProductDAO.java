package db;

import entities.ProductEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.nio.file.Path;
import java.util.List;

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

    public List<ProductEntity> findAll() {
        return list(namedQuery("db.productEntity.findAll"));
    }

    public ProductEntity create(ProductEntity productEntity) {
        return persist(productEntity);
    }

    public ProductEntity update(ProductEntity productEntity) {
        return persist(productEntity);
    }

    public ProductEntity saveOrUpdate(ProductEntity productEntity){
        return persist(productEntity);
    }

    public void delete(ProductEntity productEntity) {
        currentSession().delete(productEntity);
    }
}
