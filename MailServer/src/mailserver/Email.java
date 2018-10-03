package mailserver;

/**
 *
 * This class contains all the necessary fields for an email.
 *
 * @author Τιμολέων Λατινόπουλος
 * @aem 2763
 */
public class Email {

    private boolean isNew;
    private String sender;
    private String receiver;
    private String subject;
    private String mainBody;

    /**
     *
     * The constructor of the class.
     */
    public Email() {
        isNew = true;
        sender = null;
        receiver = null;
        subject = null;
        mainBody = null;
    }

    /**
     *
     * Sets the state isNew to the specified value.
     *
     * @param state the value that is given
     */
    public void setNew(boolean state) {
        isNew = state;
    }

    /**
     *
     * Sets the sender of the email to the specified value.
     *
     * @param sender the value that is given
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     *
     * Sets the receiver of the email to the specified value.
     *
     * @param receiver the value that is given
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     *
     * Sets the subject of the email to the specified value.
     *
     * @param subject the value that is given
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * Sets the main body of the email to the specified value.
     *
     * @param mainBody the value that is given
     */
    public void setMainBody(String mainBody) {
        this.mainBody = mainBody;
    }

    /**
     *
     * Returns a string indicating if the email was read.
     *
     * @return '[New]' if the state email wasn't read before or an empty string
     * if it was read
     */
    public String getNew() {
        if (isNew) {
            return "[New]";
        } else {
            return "";
        }
    }

    /**
     *
     * Gives the sender of the emaill.
     *
     * @return the sender of the email
     */
    public String getSender() {
        return sender;
    }

    /**
     *
     * Gives the receiver of the email.
     *
     * @return the receiver of the email
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     *
     * Gives the subject of the email
     *
     * @return the subject of the email
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * Gives the main body of the email
     *
     * @return the main body of the email
     */
    public String getMainBody() {
        return mainBody;
    }
}
