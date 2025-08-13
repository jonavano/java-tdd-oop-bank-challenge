package com.booleanuk.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BankAccount {

    private String accountName;
    private String branch;
    private List<Map.Entry<LocalDateTime, Float>> transactions;

    private float overDraftAmountAllowed;

    public BankAccount(String accountName, String branch) {
        this.accountName = accountName;
        this.branch = branch;
        this.transactions = new ArrayList<>();
        overDraftAmountAllowed = -1;
    }

    public String withdraw(float amount) {
        if (amount < 0)
            return "Not a valid transaction";

        if (getBalance() < amount)
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
        if (overDraftAmountAllowed != -1)
            return "Overdraft already approved";

        return "Overdraft requested";

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
