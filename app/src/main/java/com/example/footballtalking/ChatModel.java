package com.example.footballtalking;

public class ChatModel {

    String message;
    String user_name;





    public ChatModel() {
    }

    public ChatModel(String message , String user_name) {
        this.message = message;
        this.user_name = user_name;
    }

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
}
