package com.booleanuk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BranchTest {
    Branch branch;
    BankAccount account;
    BankAccount emptyBranch;

    @BeforeEach
    void setUp() {
        branch = new TokyoBranch();
        account = new SavingsAccount("埼玉", branch);
        emptyBranch = new CheckingAccount("", null);
    }

    @Test
    void addAccount() {
        Assertions.assertEquals("Account already part of this branch", branch.addAccount(account));

        BankAccount emptyBranch = new CheckingAccount("", null);
        Assertions.assertEquals("Account successfully registered at this branch",
                branch.addAccount(emptyBranch));
    }

    @Test
    void requestOverdraft() {
        Assertions.assertEquals("Overdraft has been requested",branch.requestOverdraft(account));
        Assertions.assertEquals("Not part of this branch", branch.requestOverdraft(emptyBranch));
        Assertions.assertEquals("Already requested overdraft",branch.requestOverdraft(account));
        branch.processOverdraft(account, "454", true);
        Assertions.assertEquals("Overdraft already approved",branch.requestOverdraft(account));

    }

    @Test
    void processOverdraft() {
        Assertions.assertEquals("No authority to approve overdrafts", branch.processOverdraft(account, "", true));
        Assertions.assertEquals("Not part of this branch", branch.processOverdraft(emptyBranch, "454", true));
        Assertions.assertEquals("No overdraft was requested", branch.processOverdraft(account, "454", true));
        branch.requestOverdraft(account);
        Assertions.assertEquals("Overdraft was rejected", branch.processOverdraft(account, "454", false));

        Assertions.assertEquals("Overdraft approved", branch.processOverdraft(account, "454", true));
    }

    @Test
    void wasApprovedOverdraft() {
        Assertions.assertFalse(branch.wasApprovedOverdraft(account));
        branch.requestOverdraft(account);
        branch.processOverdraft(account, "454", true);
        Assertions.assertTrue(branch.wasApprovedOverdraft(account));
    }
}