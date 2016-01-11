package clientserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aleksandr on 09.01.2016.
 */
public class Server implements Runnable {
    private final boolean ISEMPTY = true;
    private final String IP_ADRESS = "127.0.0.1";
    private final int PORT = 4567;

    private static ServerSocket serverSocket;
    private static Socket connection;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    @Override
    public void run() {

        while (ISEMPTY) {

            try {
                serverSocket = new ServerSocket(PORT, 10);
                //serverSocket.accept() - return Socket
                connection = serverSocket.accept();
                objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
                objectInputStream = new ObjectInputStream(connection.getInputStream());
                objectOutputStream.writeObject("you send: " + (String) objectInputStream.readObject());

            } catch (IOException ioException) {
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}