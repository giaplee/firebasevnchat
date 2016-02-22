package app.lee.giap.firechatdemo.models;

/**
 * Created by giaplv on 22/02/2016.
 */
public class ChatMessage {

    private String user_name;
    private String message;
    private String time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
