package Client;

import com.intellij.openapi.vcs.FilePath;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SocketListener extends Thread {

    @Override
    public void run() {
        System.out.println("SocketListener started.");
        try {
            while( Client.isActive() ) {
                // Check if we received something
                String message = Client.dis.readUTF();

                if (message.contentEquals("#0")) {
                    Client.close();
                    break;
                } else {
                    if (message.contentEquals("#text")) {
                        // Show message to console
                        message = Client.dis.readUTF();
                        System.out.println(message);
                    }

                    if (message.contentEquals("#file")) {
                        String title = Client.dis.readUTF();
                        Path filePath = Paths.get(title);

                        FileOutputStream fos = new FileOutputStream(String.valueOf(filePath.getFileName()));
                        BufferedOutputStream bos = new BufferedOutputStream(fos);

                        int len = Client.dis.readInt();
                        byte [] byteArray  = new byte [len];

                        Client.dis.read(byteArray);
                        bos.write(byteArray);

                        // Show message to console
                        System.out.println(title);
                    }

                }


                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Client.close();
        }
        System.out.println("SocketListener ended.");
    }
}
