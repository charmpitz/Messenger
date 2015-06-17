package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class SocketListener extends Thread {
    int id;
    DataInputStream dis;
    DataOutputStream dos;

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
                // We parse and execute the command
                CommandHandler handler = new CommandHandler(id, dis, dos);
                handler.executeCommand();

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
