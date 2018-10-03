package mailserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class contains a list with all the user accounts.
 *
 * @author Τιμολέων Λατινόπουλος
 * @aem 2763
 */
public class Database {

    private List<Account> accounts = null;
    private Account account1, account2;
    private Email email;

    /**
     *
     * The contructor of the class.
     */
    public Database() {
        accounts = new ArrayList<>();
        newAccounts();
    }

    /**
     *
     * Gives a list of all the user accounts.
     *
     * @return a list of all the user accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     *
     * Creates two new accounts with three different emails in each one and adds
     * them to the account list.
     */
    private void newAccounts() {
        account1 = new Account();
        account1.setUsername("Admin");
        account1.setPassword("0000");

        account2 = new Account();
        account2.setUsername("User1");
        account2.setPassword("1111");

        email = new Email();
        email.setReceiver("Admin");
        email.setSender("User1");
        email.setSubject("Hey there!");
        email.setMainBody("This is an email :)");
        account1.setMailbox(email);

        email = new Email();
        email.setReceiver("Admin");
        email.setSender("User1");
        email.setSubject("Hello there!");
        email.setMainBody("This is another email :)");
        account1.setMailbox(email);

        email = new Email();
        email.setReceiver("Admin");
        email.setSender("User1");
        email.setSubject("Hi!");
        email.setMainBody("This is the last email :)");
        account1.setMailbox(email);

        email = new Email();
        email.setReceiver("User1");
        email.setSender("Admin");
        email.setSubject("Welcome!");
        email.setMainBody("Thank you for coming!");
        account2.setMailbox(email);

        email = new Email();
        email.setReceiver("User1");
        email.setSender("Admin");
        email.setSubject("Welcome back!");
        email.setMainBody("Thank you for using our service!");
        account2.setMailbox(email);

        email = new Email();
        email.setReceiver("User1");
        email.setSender("Admin");
        email.setSubject("Thank you!");
        email.setMainBody("Thank you for using our service!");
        account2.setMailbox(email);

        accounts.add(account1);
        accounts.add(account2);
    }
}
