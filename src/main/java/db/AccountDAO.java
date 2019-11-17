package db;

import entities.AccountEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class AccountDAO extends AbstractDAO<AccountEntity> {

    public AccountDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public AccountEntity findById(Long id) {
        return get(id);
    }

    public Optional<AccountEntity> findByEmail(String email) {
        Query query = namedQuery("de.clickhealthy.manage.db.accountEntity.findByEmail");
        query.setParameter("email", email);
        return Optional.ofNullable(uniqueResult(query));
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
