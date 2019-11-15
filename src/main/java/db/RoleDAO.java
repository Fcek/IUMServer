package db;


import entities.RoleEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class RoleDAO extends AbstractDAO<RoleEntity> {
	/**
	 * Creates a new DAO with a given session provider.
	 *
	 * @param sessionFactory a session provider
	 */
	public RoleDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public RoleEntity findById(Long id) {
		return get(id);
	}

	public RoleEntity create(RoleEntity roleEntity) {
		return persist(roleEntity);
	}

	public RoleEntity update(RoleEntity roleEntity) {
		return persist(roleEntity);
	}

}
