package resources;


import db.AccountDAO;
import exceptions.AccountAlreadyExistsException;
import exceptions.BadPasswordException;
import exceptions.GeneralAccountProblemException;
import io.dropwizard.hibernate.UnitOfWork;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {
    private final AccountDAO accountDao;

    public MainResource(AccountDAO accountDao) {
        this.accountDao = accountDao;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("abc")
    public Object reply(){
        return Response.status(400).build();
    }
}
