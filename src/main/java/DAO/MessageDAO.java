package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
    
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
    
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return new Message(id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                }
            }
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }    

    public List<Message> getAllMessages() {
        // Returns a list of all messages from DB
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                long timePosted = rs.getLong("time_posted_epoch");

                Message message = new Message(messageId, postedBy, text, timePosted);
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int messageId) {
        // Returns a specific message by ID
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,messageId);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int postedBy = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                long timePosted = rs.getLong("time_posted_epoch");

                return new Message(messageId, postedBy, text, timePosted);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int messageId) {
        // Deletes a message by ID and returns the deleted Message if it existed
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Step 1: Retrieve the message first
            String selectSql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement selectPs = connection.prepareStatement(selectSql);
            selectPs.setInt(1, messageId);
            ResultSet rs = selectPs.executeQuery();

            if (rs.next()) {
                // Store the message details
                int postedBy = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                long timePosted = rs.getLong("time_posted_epoch");

                Message message = new Message(messageId, postedBy, text, timePosted);

                // Step 2: Delete the message
                String deleteSql = "DELETE FROM message WHERE message_id = ?;";
                PreparedStatement deletePs = connection.prepareStatement(deleteSql);
                deletePs.setInt(1, messageId);
                deletePs.executeUpdate();

                return message; // Return the deleted message
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int messageId, String newText) {
        // Updates the message_text for a given ID and returns the updated Message
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Step 1: Update the message text
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newText);
            ps.setInt(2, messageId);

            int updatedRows = ps.executeUpdate();

            // Step 2: Only continue if update was successful
            if (updatedRows > 0) {
                // Step 3: Query and return the updated message
                String selectSql = "SELECT * FROM message WHERE message_id = ?;";
                PreparedStatement selectPs = connection.prepareStatement(selectSql);
                selectPs.setInt(1, messageId);
                ResultSet rs = selectPs.executeQuery();

                if (rs.next()) {
                    int postedBy = rs.getInt("posted_by");
                    String messageText = rs.getString("message_text");
                    long timePosted = rs.getLong("time_posted_epoch");

                    return new Message(messageId, postedBy, messageText, timePosted);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessagesByUser(int accountId) {
        // Returns all messages posted by a specific account
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                String text = rs.getString("message_text");
                long timePosted = rs.getLong("time_posted_epoch");

                Message message = new Message(messageId, accountId, text, timePosted);
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
