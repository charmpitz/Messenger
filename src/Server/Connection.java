package Server;

import java.io.*;
import java.net.*;

public class Connection extends Thread {
    public Socket cs = null;
    public InputStream is = null;
    public OutputStream os = null;
    public DataInputStream dis = null;
    public DataOutputStream dos = null;

    private int id;
    private boolean status = true;

    private Thread socketListener;
    private Thread queueConsumer;

    public Connection(Socket client_socket, int client_id) {
        id = client_id;
        cs = client_socket;
    }

    @Override
    public void run() {
        System.out.println("Connection #" + id + " started.");
        try {
            _initStreams();

            /*
            * Listens to incoming commands, parses them and adds the messages to
            * the user queue
            * */
            socketListener = new SocketListener(id, dis, dos);
            socketListener.start();

            /*
            * Listens to the user message queue and consumes the messages by
            * sending them to the intended user
            * */
            queueConsumer = new QueueConsumer(id, dis, dos);
            queueConsumer.start();

            // Waiting for the threads to end
            socketListener.join();
            queueConsumer.join();

            _closeStreams();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Server.clients.remove(id);
        Server.clientsMessages.remove(id);
        System.out.println("Connection #" + id + " ended.");
    }

    private void _initStreams() throws IOException{
        is = cs.getInputStream();
        os = cs.getOutputStream();
        dis = new DataInputStream(is);
        dos = new DataOutputStream(os);
    }
    private void _closeStreams() throws IOException{
        dis.close();
        is.close();
        dos.close();
        os.close();
    }

    public boolean isActive() {
        return status;
    }

    public void close() {
        status = false;
    }
}
