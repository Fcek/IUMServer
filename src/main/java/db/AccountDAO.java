package db;

import entities.AccountEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class AccountDAO extends AbstractDAO<AccountEntity> {

    public AccountDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public AccountEntity findById(Long id) {
        return get(id);
    }

    public AccountEntity create(AccountEntity accountEntity) {
        return persist(accountEntity);
    }

    public AccountEntity update(AccountEntity accountEntity) {
        return persist(accountEntity);
    }

    public void delete(AccountEntity accountEntity) {
        currentSession().delete(accountEntity);
    }
}
