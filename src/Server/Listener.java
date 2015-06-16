package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Listener extends Thread {
    DataInputStream dis;
    DataOutputStream dos;
    int id;

    public Listener(int id, DataInputStream dis, DataOutputStream dos) {
        this.id = id;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        try {
            while( true ) {
                // We check if client wants to send something to somebody
                String line = dis.readUTF();

                if (!line.isEmpty()) {
                    MessageHandler handler = new MessageHandler(id, line);
                    handler.executeCommand();
                }

                if (line.contentEquals("/exit")) {
                    dos.writeUTF("#0");
                    break;
                }

                dos.writeUTF("#1");
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("Socket Listener " + id + " has disconnected.");
    }
}
