package myapplication.example.mapinproject.business;

import java.util.HashMap;

import myapplication.example.mapinproject.data.entities.Tweeit;

public interface TweeitCallback {
    void getTweeitCallBack(HashMap<String,Tweeit> map);
}