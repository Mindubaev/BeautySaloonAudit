package com.mycompany.beautysaloonaudit;

import java.util.ArrayList;
import java.util.List;

public class TimeManager {

    private List<TimeInterval> freeTimeIntervals;
    private List<TimeInterval> bookedTimeIntervals = new ArrayList<>();

    public TimeManager(List<TimeInterval> freeTimeIntervals) {
        this.freeTimeIntervals = freeTimeIntervals;
    }

    public boolean addBookedTimeInterval(TimeInterval interval) {
        List<TimeInterval> intervals = new ArrayList<>();
        boolean isSuitable = false;
        for (int i = 0; i < freeTimeIntervals.size(); i++) {
            TimeInterval freeTimeInterval = freeTimeIntervals.get(i);
            if (freeTimeInterval.isContains(interval)) {
                if (freeTimeInterval.getStart().getMillis() != interval.getStart().getMillis()) {
                    TimeInterval firstPart = new TimeInterval(freeTimeInterval.getStart(), interval.getStart());
                    intervals.add(firstPart);
                }
                if (freeTimeInterval.getEnd().getMillis() != interval.getEnd().getMillis()) {
                    TimeInterval secondPart = new TimeInterval(interval.getEnd(), freeTimeInterval.getEnd());
                    intervals.add(secondPart);
                }
                bookedTimeIntervals = addInChronology(interval, bookedTimeIntervals);
                isSuitable = true;
            } else {
                intervals.add(freeTimeInterval);
            }
        }
        freeTimeIntervals = intervals;
        return isSuitable;
    }

    @Override
    public String toString() {
        String info = "Доступное время:";
        for (TimeInterval interval : freeTimeIntervals) {
            info = info + "\n" + interval.toString();
        }
        info = info + "\nЗабронированное время:";
        for (TimeInterval interval : bookedTimeIntervals) {
            info = info + "\n" + interval.toString();
        }
        return info;
    }

    private List<TimeInterval> addInChronology(TimeInterval interval, List<TimeInterval> bookedTimeIntervals) {
        List<TimeInterval> intervals = new ArrayList<>();
        if (bookedTimeIntervals.size() == 0) {
            intervals.add(interval);
        } else {
            if (bookedTimeIntervals.size() > 0 && interval.getStart().isBefore(bookedTimeIntervals.get(0).getStart())) {
                intervals.add(interval);
            }
            if (bookedTimeIntervals.size()==1)
                intervals.add(bookedTimeIntervals.get(0));
            else
            {
                for (int i = 0; i < bookedTimeIntervals.size() - 1; i++) {
                    TimeInterval firstInterval = bookedTimeIntervals.get(i);
                    TimeInterval secondInterval = bookedTimeIntervals.get(i + 1);
                    intervals.add(firstInterval);
                    if (firstInterval.getStart().isBefore(interval.getStart()) && secondInterval.getStart().isAfter(interval.getStart())) {
                        intervals.add(interval);
                    }
                }
                intervals.add(bookedTimeIntervals.get(bookedTimeIntervals.size()-1));
            }
            if (bookedTimeIntervals.size() > 0 && interval.getEnd().isAfter(bookedTimeIntervals.get(0).getEnd())) {
                intervals.add(interval);
            }
        }
        return intervals;
    }

}
