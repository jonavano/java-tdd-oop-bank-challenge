package com.booleanuk.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Branch {

    private String branchLocation;
    private String managerAccessKey;
    private List<BankAccount> accounts;
    private List<BankAccount> requestedOverdraft;
    private List<BankAccount> approvedOverdraft;

    Branch(String branchLocation, String managerAccessKey1) {
        this.branchLocation = branchLocation;
        this.managerAccessKey = managerAccessKey1;
        this.accounts = new ArrayList<>();
        this.requestedOverdraft = new ArrayList<>();
        this.approvedOverdraft = new ArrayList<>();
    }

    public String addAccount(BankAccount account) {
        if (accounts.contains(account))
            return "Account already part of this branch";

        accounts.add(account);
        return "Account successfully registered at this branch";
    }

    public String requestOverdraft(BankAccount account) {
        if (!accounts.contains(account))
            return "Not part of this branch";

        if(requestedOverdraft.contains(account))
            return "Already requested overdraft";

        if (approvedOverdraft.contains(account))
            return "Overdraft already approved";

        requestedOverdraft.add(account);
        return "Overdraft has been requested";
    }

    public String processOverdraft(BankAccount account, String accessKey, Boolean approve) {
        if (!managerAccessKey.equals(accessKey))
            return "No authority to approve overdrafts";

        if (!accounts.contains(account))
            return "Not part of this branch";


        if(requestedOverdraft.contains(account) && approve) {
            requestedOverdraft.remove(account);
            approvedOverdraft.add(account);
            return "Overdraft approved";
        }

        if (!approve)
            return "Overdraft was rejected";

        return "No overdraft was requested";
    }

    public boolean wasApprovedOverdraft(BankAccount account) {
        return approvedOverdraft.contains(account);
    }
}
