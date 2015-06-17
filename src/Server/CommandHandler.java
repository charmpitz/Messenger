package Server;

import java.io.*;

public class CommandHandler {
    String command;
    int from;
    public DataInputStream dis;
    public DataOutputStream dos;

    public FileInputStream fis = null;
    public BufferedInputStream bis = null;
    public FileOutputStream fos = null;
    public BufferedOutputStream bos = null;

    public CommandHandler(int from, DataInputStream dis, DataOutputStream dos) {
        this.from = from;
        this.dis = dis;
        this.dos = dos;
    }

    public void executeCommand() {
        try {
            // We check if client wants to send something to somebody
            String command = dis.readUTF();
            System.out.println("Command started.");

            // Split the command
            String[] tokens = command.split(" ");
            int to;
            Message message = null;

            //------------------------------------------
            switch (tokens[0]) {
            /* Exit */
                case "/exit":
                    dos.writeUTF("#0");
                    Server.clients.get(from).close();
                    break;

            /* Whisper */
                case "/w":
                    to = Integer.parseInt(tokens[1]);

                    message = new Message(from, to, tokens[2], null);
                    Server.addMessageToClientQueue(to, message);

                    System.out.println(from + " told " + to + ":\t" + tokens[2]);
                    break;

            /* File Transfer */
                case "/ft":
                    to = Integer.parseInt(tokens[1]);

                    int len = Integer.parseInt(tokens[3]);
                    byte [] byteArray  = new byte [len];
                    dis.read(byteArray);

                    message = new Message(from, to, tokens[2], byteArray);
                    Server.addMessageToClientQueue(to, message);

                    System.out.println(from + " sent a file to " + to + ":\t" + tokens[2]);

                    break;

                default:
                    dos.writeUTF("#1");
            }
            //------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Command ended.");
    }
}
