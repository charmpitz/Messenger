package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class QueueConsumer extends Thread {
    int id;
    DataInputStream dis;
    DataOutputStream dos;

    public QueueConsumer(int id, DataInputStream dis, DataOutputStream dos) {
        this.id = id;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run () {
//  /ft 1 /home/ady/Pictures/ARTcast.jpg
        System.out.println("QueueConsumer #" + id + " started.");
        try {
            while( Server.clients.get(id).isActive() ) {
                Message message = Server.clientsMessages.get(id).poll();

                if (message != null) {
                    if (message.data != null) {
                        dos.writeUTF("#file");
                        dos.writeUTF(message.text);
                        dos.writeInt(message.data.length);
                        dos.write(message.data);
                        dos.flush();
                    } else {
                        dos.writeUTF("#text");
                        dos.writeUTF(message.from + ":" + message.text);
                    }
                }

                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Server.clients.get(id).close();
        }
        System.out.println("QueueConsumer #" + id + " ended.");
    }

}
