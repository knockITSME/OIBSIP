package org.example;
import java.util.Scanner;

public class ATM {
    private Bank bank;
    private Scanner scanner;
    private Account currentAccount;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        System.out.println("================================");
        System.out.println("       WELCOME TO ATM");
        System.out.println("================================");

        if (!login()) {
            System.out.println("Access Denied. Too many incorrect attempts.");
            return;
        }

        showMenu();
    }

    private boolean login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            if (bank.authenticate(userId, pin)) {

                currentAccount = bank.getAccount(userId);

                System.out.println("\nLogin Successful!");
                return true;
            }

            attempts++;

            System.out.println("Invalid User ID or PIN.");

            if (attempts < 3) {
                System.out.println("Attempts remaining: "
                        + (3 - attempts));
            }
        }

        return false;
    }

    private void showMenu() {

        int choice = 0;

        do {

            System.out.println("\n================================");
            System.out.println("          ATM MENU");
            System.out.println("================================");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid option.");
                continue;
            }

            switch (choice) {

                case 1:
                    showTransactionHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    System.out.println("\nThank you for using our ATM.");
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }

    private void showTransactionHistory() {

        System.out.println("\n========= TRANSACTION HISTORY =========");

        if (currentAccount.getTransactions().isEmpty()) {
            System.out.println("No transactions found.");
        } else {

            for (Transaction transaction :
                    currentAccount.getTransactions()) {

                System.out.println(transaction);
            }
        }

        System.out.println("=======================================");
        System.out.printf("Current Balance: ₹%.2f%n",
                currentAccount.getBalance());
    }

    private void withdraw() {

        System.out.print("Enter withdrawal amount: ");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient Funds");
            return;
        }

        currentAccount.withdraw(amount);

        Transaction transaction = new Transaction(
                "WITHDRAW",
                amount,
                "Cash withdrawal"
        );

        currentAccount.addTransaction(transaction);

        System.out.printf("₹%.2f withdrawn successfully.%n", amount);
        System.out.printf("Remaining Balance: ₹%.2f%n",
                currentAccount.getBalance());
    }

    private void deposit() {

        System.out.print("Enter deposit amount: ");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        currentAccount.deposit(amount);

        Transaction transaction = new Transaction(
                "DEPOSIT",
                amount,
                "Cash deposit"
        );

        currentAccount.addTransaction(transaction);

        System.out.printf("₹%.2f deposited successfully.%n", amount);
        System.out.printf("Current Balance: ₹%.2f%n",
                currentAccount.getBalance());
    }

    private void transfer() {

        System.out.print("Enter recipient account ID: ");
        String recipientId = scanner.nextLine();

        Account recipient = bank.getAccount(recipientId);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        if (recipient == currentAccount) {
            System.out.println("Cannot transfer to your own account.");
            return;
        }

        System.out.print("Enter transfer amount: ");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient Funds");
            return;
        }

        currentAccount.withdraw(amount);
        recipient.deposit(amount);

        Transaction senderTransaction = new Transaction(
                "TRANSFER",
                amount,
                "Transferred to " + recipientId
        );

        currentAccount.addTransaction(senderTransaction);

        Transaction recipientTransaction = new Transaction(
                "TRANSFER",
                amount,
                "Received from " + currentAccount.getUserId()
        );

        recipient.addTransaction(recipientTransaction);

        System.out.printf("₹%.2f transferred successfully.%n", amount);
        System.out.printf("Current Balance: ₹%.2f%n",
                currentAccount.getBalance());
    }
}
