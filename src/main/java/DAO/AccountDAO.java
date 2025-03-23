package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    public Account insertAccount(Account account) {
        // TODO: Write SQL to insert new account and return inserted Account with generated ID
        return null;
    }

    public Account getAccountByUsername(String username) {
        // TODO: Query DB for account with given username
        return null;
    }

    public Account getAccountByUsernameAndPassword(String username, String password) {
        // TODO: Query DB for account matching both username and password
        return null;
    }

}
