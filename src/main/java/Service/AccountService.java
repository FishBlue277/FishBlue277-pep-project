package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO(); // You could also inject this for testability
    }

    // 1. Register a new account
    public Account register(Account account){
        if (account.getUsername() != null &&
            !account.getUsername().isBlank() &&
            account.getPassword() != null &&
            account.getPassword().length() >= 4 &&
            accountDAO.getAccountByUsername(account.getUsername()) == null) {

            return accountDAO.insertAccount(account);
        }
        return null;
    }

    // 2. Login an account
    public Account login(Account account){
        return accountDAO.getAccountByUsernameAndPassword(
            account.getUsername(), account.getPassword()
        );
    }
}
