package myapplication.example.mapinproject.data.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateRange {

    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh時mm分ss秒");
    private Calendar startDate;
    private Calendar endDate;

    public DateRange(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateRange(Calendar startDate) {
        this.startDate = startDate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        endDate = calendar;
    }

    public DateRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        endDate = calendar;

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        startDate = calendar;
    }

    public String getStartDate() {
        return format.format(startDate.getTime());
    }

    public String getEndDate() {
        return format.format(endDate.getTime());
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange dateRange = (DateRange) o;
        return Objects.equals(startDate, dateRange.startDate) &&
                Objects.equals(endDate, dateRange.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
