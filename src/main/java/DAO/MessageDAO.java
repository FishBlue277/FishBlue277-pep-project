package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message) {
        // Inserts a message into DB and returns the inserted Message (with generated ID)
        return null;
    }

    public List<Message> getAllMessages() {
        // Returns a list of all messages from DB
        return null;
    }

    public Message getMessageById(int messageId) {
        // Returns a specific message by ID
        return null;
    }

    public Message deleteMessageById(int messageId) {
        // Deletes a message by ID and returns the deleted Message if it existed
        return null;
    }

    public Message updateMessage(int messageId, String newText) {
        // Updates the message_text for a given ID and returns the updated Message
        return null;
    }

    public List<Message> getMessagesByUser(int accountId) {
        // Returns all messages posted by a specific account
        return null;
    }
}
