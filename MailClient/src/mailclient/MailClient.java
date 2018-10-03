package mailclient;

import java.net.*;
import java.io.*;

/**
 *
 * This class is the client which connects to the mail server by using a socket.
 * It first takes input from the user, sends it to the server and then it waits
 * for the corresponding server output. If the server returns "exit", then the
 * loop breaks and the client releases all it's resources.
 *
 * @author Τιμολέων Λατινόπουλος
 * @aem 2763
 */
public class MailClient {

    public static final int DEFAULT_SERVER_PORT = 5050;

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Socket mailSocket = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        BufferedReader read = null;

        String host = args[0];       //the first argument of the main class that indicates the server's ip address

        try {
            mailSocket = new Socket(host, DEFAULT_SERVER_PORT);        //the socket which connects with the server
            out = new DataOutputStream(mailSocket.getOutputStream());       //the ouput which goes to the socket
            in = new DataInputStream(mailSocket.getInputStream());          //the input that comes from the socket
            read = new BufferedReader(new InputStreamReader(System.in));    //the buffered reader for user input

            String fromUser, fromServer;
            while ((fromServer = in.readUTF()) != null) {
                System.out.println("------------\nMail Server:\n------------\n" + fromServer);

                if (fromServer.toLowerCase().equals("exit")) {
                    break;
                }

                fromUser = read.readLine();
                if (fromUser != null) {
                    out.writeUTF(fromUser);
                }
            }
        } catch (IOException e) {
            System.err.println("readline: " + e.getMessage());
        } finally {
            if (mailSocket != null) {
                try {
                    System.out.println("Client cleaning up ... ");
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (read != null) {
                        read.close();
                    }
                    mailSocket.close();
                } catch (IOException e) {
                    System.err.println("close: " + e.getMessage());
                }
            }
        } //finally
    } //catch
}
