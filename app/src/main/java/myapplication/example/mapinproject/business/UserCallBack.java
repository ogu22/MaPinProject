package myapplication.example.mapinproject.business;

import java.util.HashMap;

import myapplication.example.mapinproject.data.entities.User;

public interface UserCallBack {
    void getUserCallBack(HashMap<String,User> map);
}
