package resources;


import com.codahale.metrics.annotation.Timed;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import core.Account;
import core.Product;
import db.AccountDAO;
import db.GoogleDAO;
import db.ProductDAO;
import entities.AccountEntity;
import entities.GoogleEntity;
import entities.ProductEntity;
import exceptions.GeneralAccountProblemException;
import io.dropwizard.hibernate.UnitOfWork;
import wrapper.Wrapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
//TODO: RESOLVE SYNC DATA

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {
    private final AccountDAO accountDAO;
    private final ProductDAO productDAO;
    final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
    private final GoogleDAO googleDAO;

    public MainResource(AccountDAO accountDAO, ProductDAO productDAO, GoogleDAO googleDAO) throws GeneralSecurityException, IOException {
        this.accountDAO = accountDAO;
        this.productDAO = productDAO;
        this.googleDAO = googleDAO;
    }

    @GET
    @Path("product")
    @UnitOfWork
    public Product getProduct(@QueryParam("q") int id){
        return Wrapper.wrapProduct(productDAO.findById((long) 1));
    }

    @GET
    @Path("products")
    @Timed
    @UnitOfWork
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        for (ProductEntity productEntity:productDAO.findAll()) {
            products.add(Wrapper.wrapProduct(productEntity));
        }
        return products;  //TODO: MAKE IT RETURN SORTED LIST BY ID
    }

    @GET
    @Path("account")
    @UnitOfWork
    public Object getAccount(@QueryParam("q") String email) throws GeneralAccountProblemException {
        if (accountDAO.findByEmail(email).isPresent()) {
            return Wrapper.wrapAccount(accountDAO.findByEmail(email).get());
        }
        throw new GeneralAccountProblemException("Account doesn't exist");
    }
    @GET
    @Path("account/id")
    @UnitOfWork
    public Account getAccountId(@QueryParam("q") Long id){
        return Wrapper.wrapAccount(accountDAO.findById(id));
    }

    @POST
    @Path("create/account")
    @UnitOfWork
    public Account createAccount(Account account){
        AccountEntity accountEntity = Wrapper.wrapAccount(account);
        accountEntity.setCreated(new Date());
        accountEntity.setActive(true);
        accountEntity.setSalt("adssfgasdas");
        accountDAO.create(accountEntity);
        return account;
    }

    @POST
    @Path("create/product")
    @UnitOfWork
    public Product createProduct(Product product){
        ProductEntity productEntity = Wrapper.wrapProduct(product);
        productDAO.create(productEntity);
        return product;
    }

    @PUT
    @Path("update/product")
    @UnitOfWork
    public Product updateProduct(Product product){
        ProductEntity productEntity = Wrapper.wrapProduct(product);
        productEntity.setUpdated(new Date());
        productDAO.update(productEntity);
        return product;
    }

    @DELETE
    @Path("delete/product")
    @UnitOfWork
    public Product deleteProduct(@QueryParam("q") Long id){
        productDAO.delete(productDAO.findById(id));
        Product product = new Product();
        return product;
    }

    @POST
    @Path("google")
    @UnitOfWork
    public String verify(String idToken) throws GeneralSecurityException, IOException {
        System.out.println(idToken);
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("906890859746-9iavesjt2dve9irplpt5hg31m859ekkk.apps.googleusercontent.com"))
                .build();
        String id = idToken.replace("\"", "");
        GoogleIdToken token = verifier.verify(id);

        if (token != null) {
            Payload payload = token.getPayload();

            System.out.println(payload.getEmail());
            GoogleEntity googleEntity = new GoogleEntity();
            googleEntity.setUserId(payload.getSubject());
            googleEntity.setEmail(payload.getEmail());
            googleEntity.setLogged(new Date());
            googleDAO.create(googleEntity);
            return "true";
            //return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return null;
        }
    }
}
