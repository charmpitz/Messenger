package Client;

import java.io.*;

public class CommandHandler {
    String command;
    public FileInputStream fis = null;
    public BufferedInputStream bis = null;
    public FileOutputStream fos = null;
    public BufferedOutputStream bos = null;

    public CommandHandler(String command) {
        this.command = command;
    }

    public void executeCommand() {
        try {
            System.out.println("Command started.");
            // Split the command
            String[] tokens = command.split(" ");

            //------------------------------------------
            switch (tokens[0]) {
                /* Exit */
                case "/exit":
                    Client.dos.writeUTF(command);
                    Client.close();
                    break;

                /* Whisper */
                case "/w":
                    Client.dos.writeUTF(command);
                    break;

                /* File Transfer */
                case "/ft":
                    // For reading Files
                    fis = new FileInputStream(tokens[2]);
                    bis = new BufferedInputStream(fis);

                    int len = fis.available();
                    byte [] byteArray  = new byte [len];
                    bis.read(byteArray, 0, len);

                    Client.dos.writeUTF(command + " " + len);
                    Client.dos.write(byteArray);
                    Client.dos.flush();
                    System.out.println("Transfer ended. " + len);
                    bis.close();
                    fis.close();
                    break;

                default:
                    /*// For writing files

                    */
            }
            //-------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Command ended.");
    }
}
