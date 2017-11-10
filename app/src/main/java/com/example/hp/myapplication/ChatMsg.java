package com.example.hp.myapplication;

/**
 * Created by hp on 11/7/2017.
 */

public class ChatMsg {
    String name;
    String message;
    public ChatMsg()
    {}
    public ChatMsg(String name,String message)
    {
        this.name=name;
        this.message=message;

    }
    public String getName()
    {
        return name;
    }
    public String getMessage()
    {
        return message;
    }
}
