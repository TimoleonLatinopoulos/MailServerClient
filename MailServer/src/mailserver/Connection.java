package mailserver;

import java.net.*;
import java.io.*;

/**
 *
 * This class is responsible for passing the user input from the client socket
 * to an instance of MailProtocol class, which returns the corresponding output.
 * It also takes care of all the exceptions and releases all it's resources upon
 * completion.
 *
 * @author Τιμολέων Λατινόπουλος
 * @aem 2763
 */
public class Connection extends Thread {

    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private MailProtocol protocol = null;
    private Database data = null;
    private String inputLine, outputLine;

    /**
     *
     * The constructor of the class.
     *
     * @param socket the server socket
     * @param data the object which contains all the data that is needed for
     * each user
     */
    public Connection(Socket socket, Database data) {
        super("Connection");
        this.socket = socket;
        this.data = data;

        System.out.println("Request from client " + this.socket.getInetAddress() + " at port " + this.socket.getPort());
    }

    /**
     *
     * The run method that runs for each client thread independently. It manages
     * the input and ouputs of the user from the client and creates an instance
     * of the MailProtocol class that processes all the input and gives the
     * according output.
     */
    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            protocol = new MailProtocol(data);
            protocol.setInitial();

            outputLine = protocol.processData(null);
            out.writeUTF(outputLine);

            while ((inputLine = in.readUTF()) != null) {
                outputLine = protocol.processData(inputLine);
                out.writeUTF(outputLine);

                if (outputLine.equals("exit")) {
                    break;
                }
            }
        } catch (EOFException e) {
            System.err.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("readline: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    System.out.println("Dispatching client " + this.socket.getInetAddress() + " at port " + this.socket.getPort());
                    socket.close();
                } catch (IOException e) {
                    System.err.println("readline: " + e.getMessage());
                }
            }
        } //finally
    } //catch
}
