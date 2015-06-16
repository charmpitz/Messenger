package Server;

public class Message {
    protected int from;
    protected int to;
    protected String text;

    public Message(int from, int to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }
}
