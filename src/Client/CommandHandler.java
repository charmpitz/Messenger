package Client;

import com.google.gson.JsonObject;
import org.apache.commons.net.util.Base64;

import java.io.*;

public class CommandHandler {
    String command;
    JsonObject jsonObject = new JsonObject();

    public CommandHandler(String command) {
        this.command = command;
    }

    public void send() throws IOException{
        Client.dos.writeUTF(jsonObject.toString());
        Client.dos.flush();
    }

    public void parseCommand() {
        try {
            System.out.println("Command started.");
            // Split the command
            String[] tokens = command.split(" ");

            //------------------------------------------
            switch (tokens[0]) {
                /* Exit */
                case "/exit":
                    jsonObject.addProperty("command", "exit");
                    Client.close();
                    break;

                /* Whisper */
                case "/w":
                    jsonObject.addProperty("command", "whisper");
                    jsonObject.addProperty("to", tokens[1]);
                    jsonObject.addProperty("text", tokens[2]);
                    break;

                /* File Transfer */
                case "/ft":
                    System.out.println("Transfer started.");

                    // For reading Files
                    FileInputStream fis = new FileInputStream(tokens[2]);
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    int len = fis.available();
                    byte [] byteArray  = new byte [len];
                    bis.read(byteArray, 0, len);

                    jsonObject.addProperty("command", "file-transfer");
                    jsonObject.addProperty("to", tokens[1]);
                    jsonObject.addProperty("text", tokens[2]);
                    jsonObject.addProperty("data", Base64.encodeBase64String(byteArray));

                    bis.close();
                    fis.close();

                    System.out.println("Transfer ended. " + len);
                    break;

                default:
            }
            //-------------------------------------------

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Command ended.");
    }
}
