package Server;

import com.google.gson.JsonObject;
import org.apache.commons.net.util.Base64;

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
        System.out.println("QueueConsumer #" + id + " started.");
        try {
            JsonObject jsonObject = new JsonObject();

            while( Server.clients.get(id).isActive() ) {
                Message message = Server.clientsMessages.get(id).poll();

                // if we found a new message on the queue
                if (message != null) {
                    jsonObject.addProperty("type", message.type);
                    jsonObject.addProperty("from", message.from);
                    jsonObject.addProperty("text", message.text);

                    if (message.data != null) {
                        // FILE
                        jsonObject.addProperty("data", message.data);
                    }

                    // send message and clean the buffer
                    dos.writeUTF(jsonObject.toString());
                    dos.flush();
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
