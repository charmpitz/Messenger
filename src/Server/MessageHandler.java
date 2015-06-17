package Server;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MessageHandler {
    int from;
    public DataInputStream dis;
    public DataOutputStream dos;
    public JsonObject jsonObject = null;
    public Message message;

    public MessageHandler(int from, DataInputStream dis, DataOutputStream dos) {
        this.from = from;
        this.dis = dis;
        this.dos = dos;
    }

    public void readMessage() throws IOException {
        String jsonString = dis.readUTF();
        jsonObject = (new Gson()).fromJson(jsonString, JsonObject.class);
        System.out.println("Command parsed: " + jsonObject);
    }

    public void executeCommand() {
        try {
            // We check if client wants to send something to somebody
            String command = jsonObject.get("command").getAsString();

            String type;
            int to;
            String text;
            String data;

            System.out.println("Command started.");
            switch (command) {
                case "exit":
                    Server.clients.get(from).close();
                    break;

                case "whisper":
                    System.out.println("whisper clause");
                    type = "text";
                    to = jsonObject.get("to").getAsInt();
                    text = jsonObject.get("text").getAsString();

                    message = new Message(type, from, to, text, null);
                    Server.addMessageToClientQueue(to, message);

                    System.out.println(from + " told " + to + ":\t" + text);
                    break;

                case "file-transfer":
                    type = "file";
                    to = jsonObject.get("to").getAsInt();
                    text = jsonObject.get("text").getAsString();
                    data = jsonObject.get("data").getAsString();

                    message = new Message(type, from, to, text, data);
                    Server.addMessageToClientQueue(to, message);

                    System.out.println(from + " sent a file to " + to + ":\t" + text);

                    break;

                default:
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Command ended.");
    }
}
