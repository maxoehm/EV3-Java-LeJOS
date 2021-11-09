package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String response = in.readLine();
        if ("conEv3".equals(response)) {
            out.println("Connection successful, starting private listener  on port " + port);
        } else {
            out.println("Connection failed, closing connection");
        }
    }

    public String receiveMessage() throws IOException {
        if (in.readLine() != null) {
            out.println("Received Command, executing ");
            return in.readLine();
        }
        return "";
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}