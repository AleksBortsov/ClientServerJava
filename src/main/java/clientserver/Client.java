package clientserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by aleksandr on 09.01.2016.
 *
 * @version 1.0
 * @created 09.07.2013
 */
public class Client extends JFrame implements Runnable {
    private final boolean ISEMPTY = true;
    private final String IP_ADRESS = "127.0.0.1";
    private final int PORT = 4567;

    private static Socket connection;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream objectInputStream;

    public static void main(String... args) {
        /*
        *   create object thead from Thead Class
        *   call constructor from Client with initialialization object
        *   the window name "TestServer"
        */

        new Thread(new Client("TestServer")).start();
        new Thread(new Server()).start();
    }

    public Client(String string) {
        // refers to the object of the parent class
        super(string);
        //create window and flow for window
        setLayout(new FlowLayout());
        //size of window
        setSize(300, 300);
        // line create stop JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // visible or not the window
        setVisible(true);
        // location window in the middle of monitor window
        setLocationRelativeTo(null);

        // create text field with size 10
        final JTextField jTextField = new JTextField(10);
        // create button  "SendMessage"
        final JButton jButton = new JButton("SendMessage");
        /*
         *   create ActionListrer for jButton. When we click fof jButton our will send message
         *   ew use inner class
         */
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == jButton) {
                    sendData(jTextField.getText());
                }
            }
        });

        //add jTextField &  jButton to window
        add(jButton);
        add(jTextField);

    }

    @Override
    public void run() {
        while (ISEMPTY) {
            try {

                connection = new Socket(InetAddress.getByName(IP_ADRESS), PORT);
                outputStream = new ObjectOutputStream(connection.getOutputStream());
                objectInputStream = new ObjectInputStream(connection.getInputStream());
                JOptionPane.showMessageDialog(null, (String) objectInputStream.readObject());

            } catch (IOException ioException) {
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendData(Object object) {
        try {
            // flush()- out all information from thread
            outputStream.flush();
            // writeObject(object) - write any object who we send to server
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
