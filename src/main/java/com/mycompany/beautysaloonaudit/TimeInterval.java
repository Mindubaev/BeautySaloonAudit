package com.mycompany.beautysaloonaudit;

import org.joda.time.DateTime;

public class TimeInterval {

    private DateTime start;
    private DateTime end;

    public TimeInterval(DateTime start, DateTime end) {
        if (start.isBefore(end)) {
            this.start = start;
            this.end = end;
        }
        else
        {
            this.start=end;
            this.end=start;
        }
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public long getDurationInMillis() {
        return end.getMillis() - start.getMillis();
    }

    public boolean isContains(DateTime dateTime) {
        if (start.isBefore(dateTime) && end.isAfter(dateTime)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isContains(TimeInterval interval) {
        if (((start.isBefore(interval.getStart()) || start.getMillis()==interval.getStart().getMillis()) && end.isAfter(interval.getStart())) && (start.isBefore(interval.getEnd()) && (end.getMillis()==interval.getEnd().getMillis() || end.isAfter(interval.getEnd())))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String str="";
        if (getEnd()!=null && getStart()!=null)
        {
            DateTime start=getStart();
            DateTime end=getEnd();
            str=start.getHourOfDay()+":"+start.getMinuteOfHour()+" - "+end.getHourOfDay()+":"+end.getMinuteOfHour();
        }
        return str;
    }

}
