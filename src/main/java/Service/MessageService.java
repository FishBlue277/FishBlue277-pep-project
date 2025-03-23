package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO; // Used for validation (e.g., posted_by exists)

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    // 1. Create a new message (with validation)
    public Message createMessage(Message message){
        if (!message.getMessage_text().isBlank() &&
            message.getMessage_text().length() <= 255 &&
            accountDAO.getAccountById(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    // 2. Get all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // 3. Get message by ID
    public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    // 4. Delete a message by ID
    public Message deleteMessageById(int messageId){
        return messageDAO.deleteMessageById(messageId);
    }

    // 5. Update a message's text by ID
    public Message updateMessage(int messageId, String newText){
        if (newText != null && !newText.isBlank() && newText.length() <= 255){
            return messageDAO.updateMessage(messageId, newText);
        }
        return null;
    }

    // 6. Get messages by user ID
    public List<Message> getMessagesByUser(int accountId){
        return messageDAO.getMessagesByUser(accountId);
    }
}
