package org.example;
import java.util.HashMap;


public class Bank {
    private HashMap<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();

        // Sample accounts
        accounts.put("user101",
                new Account("user101", "1234", 10000));

        accounts.put("user102",
                new Account("user102", "5678", 5000));
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }

    public boolean authenticate(String userId, String pin) {

        Account account = accounts.get(userId);

        if (account != null && account.getPin().equals(pin)) {
            return true;
        }

        return false;
    }
}
