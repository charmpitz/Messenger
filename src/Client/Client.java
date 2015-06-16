package Client;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    static Scanner scanner = new Scanner(System.in);
    static Socket ss = null;
    static InputStream is;
    static OutputStream os;
    static DataInputStream dis;
    static DataOutputStream dos;
    static String address;
    static int port;
    static Thread listener;
    static boolean status = true;

    public static void main(String[] args) throws Exception {

        System.out.print("IP Address: ");
        address = scanner.next();

        System.out.print("Port: ");
        port = scanner.nextInt();

        ss = new Socket(address, port);

        os = ss.getOutputStream();
        dos = new DataOutputStream(os);
        is = ss.getInputStream();
        dis = new DataInputStream(is);

        listener = new Listener(dis);
        listener.start();

        while( isActive() ) {
            // Read from the keyboard if something is sent
            String line = scanner.nextLine();

            if(!line.isEmpty()) {
                dos.writeUTF(line);

                // check for exit
                if (line.contentEquals("/exit")) {
                    listener.interrupt();
                    status = false;
                }
            }

            Thread.sleep(100);
        }

        is.close();
        os.close();

        System.out.println(listener.isAlive());
        System.out.println("You have been disconnected");
    }

    public static boolean isActive() {
        return status;
    }

}
