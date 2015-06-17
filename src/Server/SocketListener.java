package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class SocketListener extends Thread {
    int id;
    DataInputStream dis;
    DataOutputStream dos;
    MessageHandler handler;

    public SocketListener(int id, DataInputStream dis, DataOutputStream dos) {
        this.id = id;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        System.out.println("SocketListener #" + id + " started.");
        try {
            while( Server.clients.get(id).isActive() ) {

                // When we got something new on the stream
                if (dis.available() != 0) {
                    // We parse and execute the command
                    handler = new MessageHandler(id, dis, dos);
                    handler.readMessage();
                    handler.executeCommand();
                }

                Thread.sleep(100);
            }

            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Server.clients.get(id).close();
        }
        System.out.println("SocketListener #" + id + " ended.");
    }
}
