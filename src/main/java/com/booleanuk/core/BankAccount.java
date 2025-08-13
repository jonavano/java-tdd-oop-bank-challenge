package com.booleanuk.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BankAccount {

    private String accountName;
    private Branch branch;
    private List<Map.Entry<LocalDateTime, Float>> transactions;

    public BankAccount(String accountName, Branch branch) {
        this.accountName = accountName;

        this.transactions = new ArrayList<>();
        this.branch = branch;
        if (branch != null)
            branch.addAccount(this);
    }

    public String withdraw(float amount) {
        boolean overdraftAllowed = branch.wasApprovedOverdraft(this);

        if (amount < 0)
            return "Not a valid transaction";

        if (getBalance() < amount && !overdraftAllowed)
            return "Not enough balance";


        transactions.add(Map.entry(LocalDateTime.now(), - amount));
        return "Withdraw completed";
    }

    public String deposit(float amount) {
        if (amount < 0)
            return "Not a valid transaction";

        transactions.add(Map.entry(LocalDateTime.now(),amount));
        return "Amount successfully deposited";
    }

    public List<Map.Entry<LocalDateTime, Float>> getStatements() {
        return transactions;
    }

    public String getStatementsToString() {
        return "";
    }

    public String requestOverdraft() {
        return branch.requestOverdraft(this);
    }

    public float getBalance() {
        return getBalance(-1);
    }

    public float getBalance(int transactionCount) {

        if (transactionCount < 0) {
            transactionCount = transactions.size();
        }
        float balance = 0;
        for (int i = 0; i < transactionCount; i++) {
            var transaction = transactions.get(i);
            balance += transaction.getValue();
        }
        return balance;
    }

}
