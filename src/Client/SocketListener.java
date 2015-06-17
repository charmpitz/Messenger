package Client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;

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
                if (Client.dis.available() != 0) {

                    String jsonString = Client.dis.readUTF();
                    JsonObject jsonObject = (new Gson()).fromJson(jsonString, JsonObject.class);

                    String type = jsonObject.get("type").getAsString();
                    int from = jsonObject.get("from").getAsInt();
                    //String to = jsonObject.get("to").getAsString();
                    String text = jsonObject.get("text").getAsString();
                    String data = null;



                    if (type.contentEquals("text")) {
                        // Show message to console
                        System.out.println(from + ": " + text);
                    }

                    if (type.contentEquals("file")) {

                        if (jsonObject.has("data")) {
                            // FILE
                            data = jsonObject.get("data").getAsString();

                            Path filePath = Paths.get(text);

                            FileOutputStream fos = new FileOutputStream(String.valueOf(filePath.getFileName()));
                            BufferedOutputStream bos = new BufferedOutputStream(fos);

                            bos.write(Base64.decodeBase64(data));
                            bos.flush();
                        }

                        // Show message to console
                        System.out.println(from + ": " + text);
                        System.out.println(data);
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
