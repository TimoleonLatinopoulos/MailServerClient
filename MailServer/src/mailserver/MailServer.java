package mailserver;

import java.net.*;
import java.io.*;

/**
 *
 * This class creates a server socket and a class which contains all the data
 * that is needed for each user. Then it passes both to the constructor of a new
 * Connection object. Finally it takes care of all the exceptions and releases
 * all it's resources upon completion.
 *
 * @author Τιμολέων Λατινόπουλος
 * @aem 2763
 */
public class MailServer {

    public static final int DEFAULT_SERVER_PORT = 5050;
    public static final int DEFAULT_SERVER_BACKLOG = 10;

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Database data = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(DEFAULT_SERVER_PORT, DEFAULT_SERVER_BACKLOG);
            data = new Database();

            System.out.println("Server waiting for clients ... ");

            while (listening) {
                new Connection(serverSocket.accept(), data).start();
            }
        } catch (IOException e) {
            System.err.println("Socket: " + e.getMessage());
        } finally {
            try {
                System.out.println("Server cleaning up ... ");
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.err.println("close: " + e.getMessage());
            }
        } //finally
    } //catch
}
