package Server;

public class Message {
    public int from;
    public int to;
    public byte[] data;
    public String text;


    public Message(int from, int to, String text, byte[] data) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.data = data;
    }
}
