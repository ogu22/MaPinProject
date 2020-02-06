package myapplication.example.mapinproject.business;

import java.util.HashMap;

import myapplication.example.mapinproject.data.entities.Reply;

public interface ReplyCallBack {
    void getReplyCallBack(HashMap<String, Reply> map);
}
