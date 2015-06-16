package Server;

import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

class Server {
    static Scanner scanner = new Scanner(System.in);
    static int conn_count = 0;
    static HashMap<Integer, ConcurrentLinkedQueue<Message>> clientsMessages;

    public static void main(String[] args) {
        try {
            clientsMessages = new HashMap<>();

            System.out.print("Enter the port: ");
            int port = scanner.nextInt();

            ServerSocket server_socket = new ServerSocket(port);

            while (true) {

                Socket cs = server_socket.accept();

                // Start a new connection
                conn_count++;
                Connection conn = new Connection(cs, conn_count);
                conn.start();

                clientsMessages.put(conn_count, new ConcurrentLinkedQueue<Message>());

                System.out.println("A new client (#"+ conn_count +") has connected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("Server has been closed.");
    }

    public static void addMessageToClientQueue(int id, Message message) {
        synchronized (clientsMessages.get(id)) {
            clientsMessages.get(id).add(message);
        }
    }
}
