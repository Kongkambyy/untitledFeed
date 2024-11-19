import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventManager {

    static ArrayList<UserAccount> accounts = new ArrayList<>();
    static ArrayList<BankInfo> info = new ArrayList<>();

    static class UserAccount {
        private String username;
        private String password;

        UserAccount(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }


    public static boolean authenticate(String username, String password) {
        for (UserAccount account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    static {
        accounts.add(new UserAccount("john", "password"));
        accounts.add(new UserAccount("jane", "password"));

        info.add(new BankInfo("john", "00000001", 1000.0));
        info.add(new BankInfo("jane", "00000002", 2000.0));
    }

    public static BankInfo getBankInfo(String username) {
        for (BankInfo bankInfo : info) {
            if (bankInfo.getUsername().equals(username)) {
                return bankInfo;
            }
        }
        return null;
    }

    public static void addNewUser(String username, String password, String accountNumber) {

        accounts.add(new UserAccount(username, password));

        info.add(new BankInfo(username, accountNumber, 0.0));
    }

    public static boolean checkIfAccountNumberExists(String accountNumber) {
        for (BankInfo bankInfo : info) {
            if (bankInfo.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    static class BankInfo {

        private String username;
        private double balance;
        private String accountNumber;

        BankInfo(String username, String accountNumber, double balance) {
            this.username = username;
            this.balance = balance;
            this.accountNumber = accountNumber;
        }

        public String getUsername() {
            return username;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
