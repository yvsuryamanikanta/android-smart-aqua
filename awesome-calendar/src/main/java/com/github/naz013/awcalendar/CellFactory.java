package com.github.naz013.awcalendar;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hirondelle.date4j.DateTime;

class CellFactory {

    private static final String TAG = "CellFactory";

    static final int ROWS = 6;
    static final int COLS = 7;

    static MonthCell getMonth(DateTime realDate, DateTime dt, DateTime anchor, int w, int ch, int oX,
                              Map<DateTime, List<Event>> map, boolean isOutMapping, int startDayOfWeek, boolean showWeekdays) {
        List<DateTime> dateTimes = getDateTimes(dt, startDayOfWeek);
        int cellWidth = w / COLS;
        int offset = calculateOffset(w, oX);
        List<WeekRow> weekRows = new ArrayList<>();
        int thisWeek = -1;
        for (int i = 0; i < ROWS; i++) {
            List<DayCell> cells = new ArrayList<>();
            for (int j = 0; j < COLS; j++) {
                int top = i * ch;
                int left = j * cellWidth;
                Rect tmp = new Rect(left + offset, top, left + cellWidth + offset, top + ch);
                DateTime dtTmp = dateTimes.get(i * 7 + j);
                DayCell dayCell = new DayCell(tmp, dtTmp, map.get(dtTmp));
                if (dayCell.getDateTime().isSameDayAs(realDate)) {
                    dayCell.setCurrent(true);
                }
                if (isOutMapping && !isSameMonth(dtTmp, dt)) {
                    dayCell.setOut(true);
                }
                if (dayCell.getDateTime().isSameDayAs(anchor) || dayCell.getDateTime().isSameDayAs(realDate)) {
                    thisWeek = i;
                }
                dayCell.setShowWeekdays(showWeekdays);
                cells.add(dayCell);
            }
            if (thisWeek == i) {
                weekRows.add(0, new WeekRow(cells));
            } else {
                weekRows.add(new WeekRow(cells));
            }
        }
        Collections.reverse(weekRows);
        MonthCell cell = new MonthCell();
        cell.setWeeks(weekRows);
        return cell;
    }

    private static boolean isSameMonth(DateTime dt, DateTime anchor) {
        return (dt.getYear().intValue() == anchor.getYear().intValue() &&
                dt.getMonth().intValue() == anchor.getMonth().intValue());
    }

    static WeekRow getWeek(DateTime realDate, DateTime dt, int w, int ch, int oX,
                           Map<DateTime, List<Event>> map, boolean isOutMapping, boolean showWeekdays) {
        List<DateTime> dateTimes = getWeekDateTimes(dt);
        int cellWidth = w / COLS;
        int offset = calculateOffset(w, oX);
        List<DayCell> cells = new ArrayList<>();
        for (int j = 0; j < COLS; j++) {
            int left = j * cellWidth;
            Rect tmp = new Rect(left + offset, 0, left + cellWidth + offset, ch);
            DayCell dayCell = new DayCell(tmp, dateTimes.get(j), map.get(dateTimes.get(j)));
            if (dayCell.getDateTime().isSameDayAs(realDate)) {
                dayCell.setCurrent(true);
            }
            if (isOutMapping && !isSameMonth(dateTimes.get(j), dt)) {
                dayCell.setOut(true);
            }
            dayCell.setShowWeekdays(showWeekdays);
            cells.add(dayCell);
        }
        return new WeekRow(cells);
    }

    private static int calculateOffset(int width, int oX) {
        if (oX > 0) {
            return width + ((int) ((float) width * 0.1f));
        } else if (oX < 0) {
            return -width - ((int) ((float) width * 0.1f));
        } else {
            return 0;
        }
    }

    private static List<DateTime> getWeekDateTimes(DateTime dt) {
        List<DateTime> dateTimes = new ArrayList<>();
        for (int i = 0; i < COLS; i++) {
            dateTimes.add(dt);
            dt = dt.plusDays(1);
        }
        return dateTimes;
    }

    private static List<DateTime> getDateTimes(DateTime dt, int startDayOfWeek) {
        List<DateTime> dateTimes = new ArrayList<>();
        DateTime firstDateOfMonth = dt.getStartOfMonth();
        DateTime lastDateOfMonth = dt.getEndOfMonth();
        int weekdayOfFirstDate = firstDateOfMonth.getWeekDay();
        if (weekdayOfFirstDate < startDayOfWeek) {
            weekdayOfFirstDate += 7;
        }
        while (weekdayOfFirstDate > 0) {
            DateTime dateTime = firstDateOfMonth.minusDays(weekdayOfFirstDate - startDayOfWeek);
            if (!dateTime.lt(firstDateOfMonth)) {
                break;
            }
            dateTimes.add(dateTime);
            weekdayOfFirstDate--;
        }
        for (int i = 0; i <= lastDateOfMonth.getDay() - 1; i++) {
            dateTimes.add(firstDateOfMonth.plusDays(i));
        }
        int endDayOfWeek = startDayOfWeek - 1;
        if (endDayOfWeek == 0) {
            endDayOfWeek = 7;
        }
        if (lastDateOfMonth.getWeekDay() != endDayOfWeek) {
            int i = 1;
            while (true) {
                DateTime nextDay = lastDateOfMonth.plusDays(i);
                dateTimes.add(nextDay);
                i++;
                if (nextDay.getWeekDay() == endDayOfWeek) {
                    break;
                }
            }
        }
        int size = dateTimes.size();
        int numOfDays = 42 - size;
        DateTime lastDateTime = dateTimes.get(size - 1);
        for (int i = 1; i <= numOfDays; i++) {
            dateTimes.add(lastDateTime.plusDays(i));
        }
        return dateTimes;
    }
}
