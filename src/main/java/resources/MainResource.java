package resources;


import com.codahale.metrics.annotation.Timed;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import core.Account;
import core.Product;
import core.Ssid;
import core.Synchronize;
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
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {
    private final AccountDAO accountDAO;
    private final ProductDAO productDAO;
    final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
    private final GoogleDAO googleDAO;
    private final List<Ssid> ssids = new ArrayList<>();

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
        return products;
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
        ProductEntity productEntity = new ProductEntity();
        productEntity.setAmount(product.getAmount());
        productEntity.setManufacturer(product.getManufacturer());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setUpdated(product.getUpdated());
        productEntity.setCreated(new Date());
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

    @PUT
    @Path("update")
    @UnitOfWork
    public Synchronize synchApp(Synchronize synchronize) {
        List<Product> offlineProducts = synchronize.getProductList();
        List<Integer> deleted = synchronize.getDeleted();

        for (Ssid ssid : ssids) {
            if (ssid.getSsid().equals(synchronize.getSsid())){
                return synchronize;
            }
            if(new Date().getTime() - ssid.getDate().getTime() > 5*60*1000){
                ssids.remove(ssid);
            }
        }
        ssids.add(new Ssid(synchronize.getSsid(), new Date()));

        for (int i : deleted) { //loop to delete products
            ProductEntity serverProduct = productDAO.findById((long) i);
            if (serverProduct != null) {
                productDAO.delete(productDAO.findById((long) i));
            }
        }

        for (Product p : offlineProducts) {
            ProductEntity productEntity = Wrapper.wrapProduct(p);
            if (p.getServerId() != null && p.getServerId() != 0) { //if product is in db
                ProductEntity serverProduct = productDAO.findById(Long.valueOf(p.getServerId()));
                if (serverProduct != null) { // if is not in db then dont insert it to the db
                    if (serverProduct.getUpdated().before(p.getUpdated()) && p.getUpdated().after(serverProduct.getUpdated())) { // check which version is newer
                        serverProduct.setUpdated(p.getUpdated());
                        serverProduct.setName(p.getName());
                        serverProduct.setManufacturer(p.getManufacturer());
                        serverProduct.setPrice(p.getPrice());
                    } else {
                        serverProduct.setUpdated(serverProduct.getUpdated());
                        serverProduct.setName(serverProduct.getName());
                        serverProduct.setManufacturer(serverProduct.getManufacturer());
                        serverProduct.setPrice(serverProduct.getPrice());
                    }
                    serverProduct.setCreated(serverProduct.getCreated());
                    serverProduct.setId(p.getServerId());
                    serverProduct.setAmount(serverProduct.getAmount() + p.getCount());
                    productDAO.update(serverProduct);
                }
            } else if(p.getServerId() == 0){
                productEntity = Wrapper.wrapProduct1(p);
                productEntity.setCreated(p.getUpdated());
                productDAO.create(productEntity);
            }
        }
        return synchronize;
    }
}
