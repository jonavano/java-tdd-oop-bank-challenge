package com.booleanuk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BankAccountTest {

    private static BankAccount account;

    @BeforeAll
    static void beforeAll() {
        account = new SavingsAccount("Sakamoto Ryouma", "Showacho");


    }

    @Test
    void withdraw() {
        Assertions.assertEquals("Not enough balance", account.withdraw(500f));
        account.deposit(500f);
        Assertions.assertEquals("Withdraw completed", account.withdraw(200));
        Assertions.assertEquals("Not enough balance", account.withdraw(400));
        Assertions.assertEquals("Not a valid transaction", account.withdraw(-500f));
    }

    @Test
    void deposit() {
        Assertions.assertEquals("Amount successfully deposited", account.deposit(42f));
        Assertions.assertEquals("Not a valid transaction", account.deposit(-420f));
    }

    @Test
    void getStatements() {
        Assertions.assertEquals(0, account.getStatements().size());
        account.deposit(500);
        account.withdraw(500);
        Assertions.assertEquals(2, account.getStatements().size());
    }

//    @Test
//    void getStatementsToString() {
//        Assertions.assertEquals("", account.getStatementsToString());
//          TODO tostring implementation
//    }


    @Test
    void requestOverdraft() {
        Assertions.assertEquals("Overdraft requested", account.requestOverdraft());
//        TODO implement overdraft and test it being already approved
        Assertions.assertEquals("Overdraft already approved", account.requestOverdraft());
    }

    @Test
    void getBalance() {
        Assertions.assertEquals(0, account.getBalance());
        account.deposit(500);
        account.deposit(52.8f);
        account.withdraw(442.27f);
        Assertions.assertEquals(110.53, account.getBalance(), 0.001);
        Assertions.assertEquals(552.8f, account.getBalance(2), 0.001);
//        TODO implement going overdraft and being in minus balance
    }

    @Test
    void testGetBalance() {
    }
}