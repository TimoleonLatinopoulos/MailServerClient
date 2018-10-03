package mailserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class contains all the necessary fields for an user account.
 *
 * @author Τιμολέων Λατινόπουλος
 * @aem 2763
 */
public class Account {

    private String username;
    private String password;
    private List<Email> mailbox;

    /**
     *
     * The constructor of the class.
     */
    public Account() {
        username = null;
        password = null;
        mailbox = new ArrayList<>();
    }

    /**
     *
     * Sets the username of the user to the specified value.
     *
     * @param username the value that is given
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * Sets the password of the user to the specified value.
     *
     * @param password the value that is given
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * Adds an email to the email List of the user.
     *
     * @param mail the value that is given
     */
    public void setMailbox(Email mail) {
        mailbox.add(mail);
    }

    /**
     *
     * Gives the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * Gives the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * Gives the list of emails of the user.
     *
     * @return the list of emails of the user
     */
    public List<Email> getMail() {
        return mailbox;
    }
}
