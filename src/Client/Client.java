package Client;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static Scanner scanner = new Scanner(System.in);

    public static Socket ss = null;

    public static InputStream is = null;
    public static OutputStream os = null;
    public static DataInputStream dis = null;
    public static DataOutputStream dos = null;

    private static String address;
    private static int port;
    private static boolean status = true;

    private static Thread socketListener;
    private static Thread consoleListener;

    public static void main(String[] args) throws Exception {

        _initStreams();

        /*
        * Listens for incoming messages and prints them to console
        * */
        socketListener = new SocketListener();
        socketListener.start();

        /*
        * Listens to console commands, parses them and does his job
        * */
        consoleListener = new ConsoleListener();
        consoleListener.start();

        // Waiting for the threads to end
        consoleListener.join();
        socketListener.join();

        _closeStreams();

        System.out.println("You have been disconnected");
    }

    private static void _initStreams() throws IOException{
        System.out.print("IP Address: ");
        address = scanner.next();

        System.out.print("Port: ");
        port = scanner.nextInt();

        ss = new Socket(address, port);

        // For sending messages
        os = ss.getOutputStream();
        dos = new DataOutputStream(os);

        // For incoming messages
        is = ss.getInputStream();
        dis = new DataInputStream(is);


    }

    private static void _closeStreams() throws IOException{
        dis.close();
        is.close();
        dos.close();
        os.close();

        ss.close();
    }

    public static boolean isActive() {
        return status;
    }

    public static void close() {
        status = false;
    }

}
