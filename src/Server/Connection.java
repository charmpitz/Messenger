package Server;

import java.io.*;
import java.net.*;

public class Connection extends Thread {
    int id;
    Socket cs = null;
    InputStream is = null;
    OutputStream os = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    Thread listener;

    public Connection(Socket client_socket, int client_id) throws IOException {
        id = client_id;
        cs = client_socket;
    }

    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            os = cs.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);

            listener = new Listener(id, dis, dos);
            listener.start();

            // We check if client received something from somebody
            while( listener.isAlive() ) {
                Message message = Server.clientsMessages.get(id).poll();

                if (message != null) {
                    dos.writeUTF(message.from + ":" + message.text);
                }

                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println(id + " has disconnected.");
    }
}
