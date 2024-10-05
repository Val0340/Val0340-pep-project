package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

//import DAO.MessageDao;

import java.sql.SQLException;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public static final ObjectMapper mapper = new ObjectMapper();
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/example", this::exampleHandler);
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages",this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}",this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}",this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/message",this::getMessageByAccountIdHandler);
        


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("Log in with your account creditials");
    }

    private void postRegisterHandler (Context ctx)  {
       try{ 
        Account account = mapper.readValue(ctx.body(), Account.class);
        AccountService c = new AccountService();
        Account addedAccount = c.registerAccount(account.getUsername(),account.getPassword(),account.getAccount_id());
       // Account aId = addedAccount , account.getAccount_id();
        if(addedAccount!=null){
            ctx.json(addedAccount).status(200);
            //ctx.result()
        }
        else{
            ctx.status(400);
        }
    }catch(SQLException | JsonProcessingException e){
        ctx.status(400);
    }
        //ctx.status(200);

    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException, SQLException{
        Account account = mapper.readValue(ctx.body(), Account.class);
        AccountService c = new AccountService();
        Account accountLogin = c.loginAccount(account.getUsername(),account.getPassword());
        if(accountLogin != null){
            ctx.json(mapper.writeValueAsString(accountLogin));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException,SQLException{
        Message message = mapper.readValue(ctx.body(), Message.class);
        MessageService ms = new MessageService();
        Message addedMessage = ms.createMessage(message.getMessage_text(),message.getPosted_by(),message.getTime_posted_epoch());

        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }
    private void getMessageHandler(Context ctx)throws SQLException{
        MessageService m = new MessageService();
        List<Message> messages = m.getAllMessages();
        ctx.json(messages);
        ctx.status(200);

    }

    private void getMessageByIdHandler(Context ctx)throws SQLException{
       // Message m = new Message();
        int ms = Integer.parseInt(ctx.pathParam("message_id"));
        MessageService m = new MessageService();
        Message messages = m.getMessageById(ms);
        if (messages!= null){
            ctx.json(messages);
        }
        else{
            ctx.status(404);
        }

        ctx.status(200);
        
    }

     private void deleteMessageByIdHandler(Context ctx)throws SQLException,NumberFormatException,MismatchedInputException{
        
        int dm = Integer.parseInt(ctx.pathParam("message_id"));
        MessageService m = new MessageService();
        boolean deletedmessage = m.deleteMessageById(dm);
       if(deletedmessage){
        ctx.json(deletedmessage);
        ctx.status(200);
        
       }else {
       
       ctx.status(200);}
    }
   

    private void updateMessageByIdHandler(Context ctx)throws JsonProcessingException,SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        MessageService m = new MessageService();
        boolean addedMessage = m.updateMessage(message.message_text,message.message_id);
        if(addedMessage){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
        //ctx.status(200);
    }

    private void getMessageByAccountIdHandler(Context ctx)throws SQLException{
       // Message m = new Message();
        int p = Integer.parseInt(ctx.pathParam("account_id"));
        MessageService m = new MessageService();
        List<Message> account = m.getAllMessagesByAccountId(p);
        ctx.json(account);
        ctx.status(200);
    }

}