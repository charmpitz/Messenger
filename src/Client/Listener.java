package Client;

import java.io.*;

public class Listener extends Thread {
    DataInputStream dis;

    public Listener(DataInputStream dis) {
        this.dis = dis;
    }

    @Override
    public void run() {
        try {
            while( Client.isActive()) {
                // Check if we received something
                String message = Client.dis.readUTF();

                if (message.equals("#0")) {
                    break;
                }

                System.out.println(message);

                Thread.sleep(100);
            }

            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Socket Listener has disconnected.");
    }
}
