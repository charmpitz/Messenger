package Server;

public class MessageHandler {
    String text;
    int from;

    public MessageHandler(int from, String text) {
        this.text = text;
        this.from = from;
    }

    public void executeCommand() {
        String[] tokens = text.split(" ");

        //whisper
        if (tokens[0].equalsIgnoreCase("/w")) {
            int to = Integer.parseInt(tokens[1]);

            Message message = new Message(from, to, tokens[2]);
            Server.addMessageToClientQueue(to, message);

            System.out.println(from + " told " + to +  ":\t" + tokens[2]);
        }

    }
}
