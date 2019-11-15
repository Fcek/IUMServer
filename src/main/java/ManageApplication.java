import db.AccountDAO;
import db.ProductDAO;
import db.RoleDAO;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resources.MainResource;

public class ManageApplication extends Application<ManageConfiguration> {

    private final HibernateBundle<ManageConfiguration> hibernate = new HibernateBundle<ManageConfiguration>(AccountDAO.class, RoleDAO.class, ProductDAO.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ManageConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }};


    public static void main(String[] args) throws Exception {
        new ManageApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ManageConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(ManageConfiguration configuration, Environment environment) throws Exception {
        final AccountDAO accountDAO = new AccountDAO(hibernate.getSessionFactory());
        final RoleDAO roleDAO = new RoleDAO(hibernate.getSessionFactory());
        final ProductDAO productDAO = new ProductDAO(hibernate.getSessionFactory());

        environment.jersey().register(accountDAO);
        environment.jersey().register(roleDAO);
        environment.jersey().register(productDAO);

        final MainResource mainResource = new MainResource(accountDAO, roleDAO, productDAO);

        environment.jersey().register(mainResource);

    }

}
