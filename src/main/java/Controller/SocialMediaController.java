package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessageById);
        app.patch("/messages/{message_id}", this::handleUpdateMessage);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByUser);

        return app;
    }

    private void handleRegister(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account result = accountService.register(account);
    
        if (result != null) {
            ctx.json(result);
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void handleLogin(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account result = accountService.login(account);
    
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(401);
        }
    }

    private void handleCreateMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message result = messageService.createMessage(message);
    
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(400);
        }
    }
    
    private void handleGetAllMessages(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

    private void handleGetMessageById(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200); // Still 200, but return empty
        }
    }

    private void handleDeleteMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(messageId);
    
        if (deleted != null) {
            ctx.json(deleted);  // return the deleted message
        } else {
            ctx.status(200);    // still 200, but with empty body
        }
    }
    
    private void handleUpdateMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = ctx.bodyAsClass(Message.class);
    
        Message result = messageService.updateMessage(messageId, updatedMessage.getMessage_text());
    
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(400);  // update failed (bad ID or invalid message_text)
        }
    }    

    private void handleGetMessagesByUser(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getMessagesByUser(accountId));
    }
}