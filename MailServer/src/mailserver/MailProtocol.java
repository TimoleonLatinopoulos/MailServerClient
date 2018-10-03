package mailserver;

import java.util.List;

/**
 *
 * This class contains all the methods which are responsible for taking the
 * input from the client user and returning the according output. That happens
 * by keeping note of the state that the server is in.
 *
 * @author Τιμολέων Λατινόπουλος
 * @aem 2763
 */
public class MailProtocol {

    private int EXIT;           //holds specific values(0, 1) showing that the server is able to quit or is running another process
    private int REGISTER;       //holds specific values(-1, 0, 1, 2) showing that the server is managing the new user registration process
    private int LOGIN;          //holds specific values(-1, 0, 1, 2) showing that the server is managing the user loging-in process
    private int NEWEMAIL;       //holds specific values(0, 1, 2, 3) showing that the server is managing the process of creating a new email
    private int READEMAIL;      //holds specific values(0, 1) showing that the server is managing the process of reading a received email
    private int DELETEEMAIL;    //holds specific values(0, 1) showing that the server is managing the process of deleting a received email

    private boolean flag;
    private static String accountProcesses, mailProcesses;
    private String input, output;
    private StringBuilder build;
    private List<Account> accounts = null;
    private List<Email> list = null;
    private Account user, receiver;
    private Email email;
    private Database data;

    /**
     *
     * The constructor of the class.
     *
     * @param data the object which contains all the data that is needed for
     * each user
     */
    public MailProtocol(Database data) {
        output = null;          //indicates the message that the client user gets after every input

        accountProcesses = "\n==========\n> Login \n> SignIn \n> Exit \n==========";
        mailProcesses = "\n===============\n> NewEmail \n> ShowEmails \n> ReadEmail \n> DeleteEmail \n> Logout \n> Exit \n===============";

        this.data = data;
        accounts = this.data.getAccounts();
    }

    /**
     *
     * Sets the variables REGISTER, LOGIN and EXIT to 0, indicating that the
     * according processes are available for use.
     */
    public void setInitial() {
        output = "Hello, you connected as a guest." + accountProcesses;
        REGISTER = 0;
        LOGIN = 0;
        EXIT = 0;
    }

    /**
     *
     * Sets the variables REGISTER and LOGIN to -1, indicating that the
     * according processes are not available for use and the variables NEWEMAIL,
     * READEMAIL, DELETEMAIL and EXIT to 0, indicating that the according
     * processes are available for use.
     */
    private void setLogin() {
        output = "Welcome back " + user.getUsername() + "! " + mailProcesses;
        REGISTER = -1;
        LOGIN = -1;

        NEWEMAIL = 0;
        READEMAIL = 0;
        DELETEEMAIL = 0;
        EXIT = 0;
    }

    /**
     *
     * Gets the input of the client user and runs the according process.
     *
     * @param anInput the input that the user has given
     * @return the message that the client user gets after every input
     */
    public String processData(String anInput) {
        input = anInput;
        if (anInput != null) {
            if (input.toLowerCase().equals("exit") && EXIT == 0) {
                exit();
            } else {
                if (LOGIN >= 0 && REGISTER >= 0) {
                    if (((input.toLowerCase().equals("register") || input.replaceAll("\\s", "").toLowerCase().equals("signin")) && LOGIN == 0) || REGISTER > 0) {
                        register();
                    } else {
                        if ((input.replaceAll("\\s", "").toLowerCase().equals("login") && REGISTER == 0) || LOGIN > 0) {
                            logIn();
                        } else {
                            setInitial();
                        }
                    }
                } else {
                    if (input.replaceAll("\\s", "").toLowerCase().equals("newemail") || (NEWEMAIL != 0)) {
                        newEmail();
                    } else {
                        if (input.replaceAll("\\s", "").toLowerCase().equals("showemails")) {
                            showEmails();
                        } else {
                            if (input.replaceAll("\\s", "").toLowerCase().equals("reademail") || (NEWEMAIL == 0 && READEMAIL != 0)) {
                                readEmail();
                            } else {
                                if (input.replaceAll("\\s", "").toLowerCase().equals("deleteemail") || (NEWEMAIL == 0 && READEMAIL == 0 && DELETEEMAIL != 0)) {
                                    deleteEmail();
                                } else {
                                    if (input.replaceAll("\\s", "").toLowerCase().equals("logout")) {
                                        logOut();
                                    } else {
                                        setLogin();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return output;
    }

    /**
     *
     * Gives an ouput of exit to the server, which will force the client to shut
     * down.
     */
    private void exit() {
        output = "exit";
    }

    /**
     *
     * Asks the user for a registered username and then the coresponding
     * password. If the username is not registered or the password isn't
     * correct, then the user an appropriate message. Otherwise, they login and
     * get access to their email account.
     */
    private void logIn() {
        flag = false;

        switch (LOGIN) {
            case 0:
                output = "Type your username";
                LOGIN = 1;
                EXIT = 1;
                break;
            case 1:
                for (Account a : accounts) {
                    if (a.getUsername().equals(input)) {
                        output = "Type your password";
                        user = a;
                        LOGIN = 2;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    output = "The username is not registered" + accountProcesses;
                    LOGIN = 0;
                    EXIT = 0;
                }
                break;
            case 2:
                for (Account a : accounts) {
                    if (!user.getPassword().equals(input)) {
                        output = "The password is invalid" + accountProcesses;
                        LOGIN = 0;
                        EXIT = 0;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    setLogin();
                }
                break;
        }
    }

    /**
     *
     * Asks the user for a new username and a password, creates a new account
     * and saves it to the server.
     */
    private void register() {
        flag = false;

        switch (REGISTER) {
            case 0:
                output = "Type your username";
                REGISTER = 1;
                EXIT = 1;
                break;
            case 1:
                for (Account a : accounts) {
                    if (a.getUsername().equals(input)) {
                        output = "The username is alrady used" + accountProcesses;
                        REGISTER = 0;
                        EXIT = 0;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    user = new Account();
                    user.setUsername(input);
                    output = "Type your password";
                    REGISTER = 2;
                }
                break;
            case 2:
                user.setPassword(input);
                accounts.add(user);
                setInitial();
                break;
        }
    }

    /**
     *
     * Creates a new email and sends it to an existing receiver. It does that by
     * asking for the receiver username(while checking if it exists in the
     * registered users), a subject title, and a main body for the email.
     */
    private void newEmail() {
        flag = false;

        switch (NEWEMAIL) {
            case 0:
                output = "Receiver: ";
                NEWEMAIL = 1;
                EXIT = 1;
                break;
            case 1:
                for (Account a : accounts) {
                    if (a.getUsername().equals(input)) {
                        output = "Subject: ";
                        receiver = a;
                        email = new Email();
                        email.setSender(user.getUsername());
                        email.setReceiver(input);
                        NEWEMAIL = 2;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    output = "The receiver is not a valid user" + mailProcesses;
                    NEWEMAIL = 0;
                    EXIT = 0;
                }
                break;
            case 2:
                output = "Main Body: ";
                email.setSubject(input);
                NEWEMAIL = 3;
                break;
            case 3:
                output = "Mail sent succesfully!" + mailProcesses;
                email.setMainBody(input);
                receiver.setMailbox(email);
                NEWEMAIL = 0;
                EXIT = 0;
                break;
        }
    }

    /**
     *
     * Shows all the received emails of the client user, if there are any at
     * all.
     */
    private void showEmails() {
        list = user.getMail();
        if (list.isEmpty()) {
            output = "No emails received" + mailProcesses;
        } else {
            int counter = 0;
            build = new StringBuilder();
            build.append(String.format("%-10s %-10s %-10s %-10s\n", "Id", "", "From", "Subject"));
            for (Email mail : list) {
                counter++;
                build.append(String.format("%-10s %-10s %-10s %-10s\n", counter + ".", mail.getNew(), mail.getSender(), mail.getSubject()));
            }
            output = build.toString() + mailProcesses;
        }
    }

    /**
     *
     * Asks the user for the email id from the received emails, checks if the
     * number is a valid id and if it happens to be, it shows all the contents
     * of the entry to the user.
     */
    private void readEmail() {
        char id;
        int index;

        switch (READEMAIL) {
            case 0:
                list = user.getMail();

                if (list.isEmpty()) {
                    output = "No emails received" + mailProcesses;
                } else {
                    output = "Email id: ";
                    READEMAIL = 1;
                    EXIT = 1;
                }
                break;
            case 1:
                id = input.charAt(0);
                index = Character.getNumericValue(id);

                if (index <= list.size()) {
                    email = new Email();

                    email = list.get(Integer.parseInt(input.substring(0, 1)) - 1);
                    output = String.format("%-10s %-10s %-10s %-10s\n", "Id", "", "From", "Subject")
                            + String.format("%-10s %-10s %-10s %-10s\n", input + ".", email.getNew(), email.getSender(), email.getSubject())
                            + "\nMain Body: \t" + email.getMainBody() + mailProcesses;
                    if (!email.getNew().equals("")) {
                        email.setNew(false);
                    }
                } else {
                    output = "The email id is invalid" + mailProcesses;
                }
                READEMAIL = 0;
                EXIT = 0;
                break;
        }
    }

    /**
     *
     * Asks the user for the email id from the received emails, checks if the
     * number is a valid id and if it happens to be, it deletes the entry.
     */
    private void deleteEmail() {
        char id;
        int index;

        switch (DELETEEMAIL) {
            case 0:
                list = user.getMail();

                if (list.isEmpty()) {
                    output = "No emails received" + mailProcesses;
                } else {
                    output = "Email id: ";
                    DELETEEMAIL = 1;
                    EXIT = 1;
                }
                break;
            case 1:
                id = input.charAt(0);
                index = Character.getNumericValue(id);

                if (index <= list.size()) {
                    list.remove(index - 1);
                    output = "Mail deleted successfully!" + mailProcesses;
                } else {
                    output = "The email id is invalid" + mailProcesses;
                }
                DELETEEMAIL = 0;
                EXIT = 0;
                break;
        }
    }

    /**
     *
     * Sets the values of the variables REGISTER, LOGIN and EXIT to 0 y running
     * the setInitial() method.
     */
    private void logOut() {
        setInitial();
    }
}
