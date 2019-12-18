package myapplication.example.mapinproject;

public class ErrorEXP {

    public boolean Errorcheck(String CheckSpel) {
        int checkbit = 0;
        if (CheckSpel.equals("")) {
            checkbit = 1;
        }
        if (checkbit == 0) {
            return true;
        } else {
            return false;
        }

    }
}
