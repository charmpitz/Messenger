package Server;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

class Server {
    static Scanner scanner = new Scanner(System.in);
    static int conn_count = 0;
    static HashMap<Integer, Connection> clients;
    static HashMap<Integer, ConcurrentLinkedQueue<Message>> clientsMessages;

    public static void main(String[] args) {
        try {
            clients = new HashMap<>();
            clientsMessages = new HashMap<>();

            System.out.print("Enter the port: ");
            int port = scanner.nextInt();

            ServerSocket server_socket = new ServerSocket(port);

            // Watch for new connections
            //-------------------------------------
            while (true) {
                Socket cs = server_socket.accept();

                conn_count++;
                Connection conn = new Connection(cs, conn_count);
                conn.start();

                clients.put(conn_count, conn);
                clientsMessages.put(conn_count, new ConcurrentLinkedQueue<Message>());
            }
            //-------------------------------------

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("Server has been closed.");
    }

    synchronized
    public static void addMessageToClientQueue(int id, Message message) {
        clientsMessages.get(id).add(message);
    }
}
