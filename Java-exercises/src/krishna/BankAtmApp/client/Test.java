package krishna.BankAtmApp.client;

import krishna.BankAtmApp.model.Account;
import krishna.BankAtmApp.worker.AccountHolder;

public class Test {

    public static void main(String[] args) {
        Account account= new Account();
        AccountHolder accountHolder = new AccountHolder(account);

        Thread t1 = new Thread(accountHolder);
        Thread t2 = new Thread(accountHolder);

        t1.setName("man");
        t2.setName("women");

        t1.start();
        t2.start();
    }
}
