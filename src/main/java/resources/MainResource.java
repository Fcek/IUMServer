package resources;


import core.Account;
import db.AccountDAO;
import db.ProductDAO;
import db.RoleDAO;
import entities.AccountEntity;
import entities.ProductEntity;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {
    private final AccountDAO accountDAO;
    private final RoleDAO roleDAO;
    private final ProductDAO productDAO;

    public MainResource(AccountDAO accountDAO, RoleDAO roleDAO, ProductDAO productDAO) {
        this.accountDAO = accountDAO;
        this.roleDAO = roleDAO;
        this.productDAO = productDAO;
    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("register")
//    @UnitOfWork
//    public Object createAccount(@FormDataParam("username") String email,
//                                @FormDataParam("password") String password,
//                                @FormDataParam("confirm_password") String confirmPassword) {
//        try {
//            accountService.registerAccount(email, password, confirmPassword); //3rd parameter sets default role for new users
//            return Response.seeOther(URI.create("/login")).build();
//        } catch (BadPasswordException e) {
//            return Response.status(400);//.entity(e.getMessage()).build();
//        } catch (GeneralAccountProblemException e) {
//            return Response.status(400).entity(e.getMessage()).build();
//        } catch (AccountAlreadyExistsException e) {
//            return Response.status(400).entity(e.getMessage()).build();
//        }
//    }

    @GET
    @Path("product")
    @UnitOfWork
    public ProductEntity acc(@QueryParam("q") int id){
//        AccountEntity accountEntity = new AccountEntity();
//        accountEntity.setEmail("abc");
//        accountEntity.setPassword("abc");
//        accountEntity.setActive(true);
//        accountEntity.setSalt("abc");
//        RoleEntity roleEntity = new RoleEntity();
//        roleEntity.setName("admin");
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("abc");
        productEntity.setManufacturer("abc");
        productEntity.setPrice((float) 10.5);
        productEntity.setAmount(100);
        //productDAO.create(productEntity);
        return productDAO.findById((long) 1);
    }

    @POST
    @Path("create/account")
    @UnitOfWork
    public Account createAccount(Account account){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmail(account.getEmail());
        accountEntity.setPassword(account.getPassword());
        accountEntity.setCreated(new Date());
        accountEntity.setActive(true);
        accountEntity.setSalt("adssfgasdas");
        accountDAO.create(accountEntity);
        return account;
    }
}
