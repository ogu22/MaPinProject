package myapplication.example.mapinproject.business;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String getDate() {
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh時mm分ss秒").format(date);
        return dateFormat;
    }
}
