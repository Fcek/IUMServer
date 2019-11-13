import db.AccountDAO;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ManageApplication extends Application<ManageConfiguration> {

    private final HibernateBundle<ManageConfiguration> hibernate = new HibernateBundle<ManageConfiguration>(AccountDAO.class) {
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
        environment.jersey().register(accountDAO);
    }

}
