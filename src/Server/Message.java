package Server;

import com.google.gson.Gson;

public class Message {
    public String type;
    public int from;
    public int to;
    public String text;
    public String data;


    public Message(String type, int from, int to, String text, String data) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.text = text;
        this.data = data;
    }

/*    public json toJSON() {

        System.out.println((new Gson()).toJson(message));
        return (new Gson()).toJson();
    }*/
}
