package myapplication.example.mapinproject.business;

import myapplication.example.mapinproject.data.entities.Reply;
import myapplication.example.mapinproject.data.entities.Tag;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.data.entities.User;

public interface  DatabaseCallback {
    void getTagCallBack(Tag tag);
    void getUserCallBack(User user);
    void getReplyCallBack(Reply reply);
    void getTweeitCallBack(Tweeit tweeit);
}