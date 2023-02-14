package com.github.naz013.awcalendar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import hirondelle.date4j.DateTime;

class DayCell extends Cell {

    private static final int V_DOTS = 2;
    private static final int H_DOTS = 3;

    private Rect rect;
    private DateTime dateTime;
    private List<Event> events = new ArrayList<>();
    private List<Rect> mDots = new ArrayList<>();
    private List<Rect> mShadowDots = new ArrayList<>();

    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;

    private boolean isCurrent;
    private boolean isOut;
    private boolean showWeekdays;

    DayCell(Rect rect, DateTime dateTime, List<Event> events) {
        this.rect = rect;
        this.dateTime = dateTime;
        this.events = events;
        extractInitValues();
        setOffsetY(0);
        generateDots();
    }

    private void generateDots() {
        int vMargin = rect.height() / 3;
        int circleWidth = rect.width() / H_DOTS;
        int circleHeight = (rect.height() - vMargin) / V_DOTS;
        int rectTop = rect.top + vMargin;
        int rectLeft = rect.left;
        for (int i = 0; i < V_DOTS; i++) {
            for (int j = 0; j < H_DOTS; j++) {
                int top = i * circleHeight + rectTop;
                int left = j * circleWidth + rectLeft;
                Rect r = new Rect(left, top, left + circleWidth, top + circleHeight);
                mDots.add(r);
                mShadowDots.add(new Rect(r));
            }
        }
    }

    void setShowWeekdays(boolean showWeekdays) {
        this.showWeekdays = showWeekdays;
    }

    boolean isShowWeekdays() {
        return showWeekdays;
    }

    void setOut(boolean out) {
        isOut = out;
    }

    boolean isOut() {
        return isOut;
    }

    void setCurrent(boolean current) {
        isCurrent = current;
    }

    boolean isCurrent() {
        return isCurrent;
    }

    private void extractInitValues() {
        mLeft = rect.left;
        mTop = rect.top;
        mRight = rect.right;
        mBottom = rect.bottom;
    }

    @Override
    public int getLeft() {
        return rect.left;
    }

    @Override
    public int getTop() {
        return rect.top;
    }

    @Override
    public int getRight() {
        return rect.right;
    }

    @Override
    public int getBottom() {
        return rect.bottom;
    }

    @Override
    public void setOffsetX(int offsetX) {
        rect.left += offsetX;
        rect.right += offsetX;
        normalize();
        super.setOffsetX(rect.left - mLeft);
        this.mShadowDots.clear();
        for (Rect r : mDots) {
            Rect rr = new Rect(r);
            rr.left += getOffsetX();
            rr.right += getOffsetX();
            this.mShadowDots.add(rr);
        }
    }

    @Override
    public void setOffsetY(int offsetY) {
        rect.top += offsetY;
        rect.bottom += offsetY;
        normalize();
        super.setOffsetY(rect.top - mTop);
        this.mShadowDots.clear();
        for (Rect r : mDots) {
            Rect rr = new Rect(r);
            rr.top += getOffsetY();
            rr.bottom += getOffsetY();
            this.mShadowDots.add(rr);
        }
    }

    private void normalize() {
        if (rect.top < 0) rect.top = 0;
        else if (rect.top > mTop) rect.top = mTop;

        if (rect.bottom > mBottom) rect.bottom = mBottom;
        else if (rect.bottom < mBottom - mTop) rect.bottom = mBottom - mTop;
    }

    DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void onDraw(Canvas canvas, Painter painter) {
        canvas.drawRect(rect, painter.getBackgroundPaint());
        canvas.drawRect(rect, painter.getBorderPaint());
        drawEvents(canvas, painter);
        drawRectText("" + dateTime.getDay(), canvas, rect, painter);
        if (showWeekdays) {
            int vMargin = rect.height() / 4;
            int hMargin = rect.width() / 10;
            canvas.drawText(AwesomeCalendarView.sWeekdayTitles[dateTime.getWeekDay() - 1],
                    rect.left + hMargin, rect.top + vMargin, painter.getWeekdayMarkPaint());
        }
    }

    private void drawEvents(Canvas canvas, Painter painter) {
        if (events == null || events.isEmpty()) return;
        for (int i = 0; i < mShadowDots.size(); i++) {
            if (i >= events.size()) {
                break;
            }
            Rect r = new Rect(mShadowDots.get(i));
            Paint p = painter.getEventPaint();
            Event event = events.get(i);
            float gap = r.width() / 4f;
            if (event.color != -1) {
                p = painter.getEventShadowPaint();
                p.setColor(event.color);
            }
            if (event.shape == Shape.SQUARE) {
                canvas.drawRect(r.left + gap, r.top + gap, r.right - gap, r.bottom - gap, p);
            } else if (event.shape == Shape.DIAMOND) {
                canvas.drawPath(getDiamond(r, gap), p);
            } else if (event.shape == Shape.TRIANGLE) {
                canvas.drawPath(getTriangle(r, gap), p);
            } else {
                canvas.drawCircle(r.centerX(), r.centerY(), gap, p);
            }
        }
    }

    private Path getTriangle(Rect r, float gap) {
        Path path = new Path();
        path.moveTo(r.left + (gap * 2f), r.top + (gap * 0.5f));
        path.lineTo(r.right - (gap * 0.5f), r.bottom - gap);
        path.lineTo(r.left + (gap * 0.5f), r.bottom - gap);
        path.close();
        return path;
    }

    private Path getDiamond(Rect r, float gap) {
        Path path = new Path();
        path.moveTo(r.left + (r.width() / 2), r.top + (gap * 0.5f));
        path.lineTo(r.right - (gap * 0.5f), r.top + (r.height() / 2));
        path.lineTo(r.right - (r.width() / 2), r.bottom - (gap * 0.5f));
        path.lineTo(r.left + (gap * 0.5f), r.bottom - (r.height() / 2));
        path.lineTo(r.left + (r.width() / 2), r.top + (gap * 0.5f));
        path.close();
        return path;
    }

    private void drawRectText(String text, Canvas canvas, Rect r, Painter painter) {
        Paint paint = painter.getTextPaint();
        if (isCurrent) paint = painter.getCurrentDayPaint();
        else if (isOut) paint = painter.getOutPaint();
        int vMargin = rect.height() / 4;
        int hMargin = rect.width() / 6;
        canvas.drawText(text, rect.right - hMargin, rect.top + vMargin, paint);
    }

    @Override
    public DateTime get(int x, int y) {
        if (x >= rect.left && x < rect.right && y >= rect.top && y < rect.bottom) {
            return dateTime;
        }
        return null;
    }

    @Override
    public String toString() {
        return "[DayCell: {l - " + rect.left +
                ", t - " + rect.top +
                ", r - " + rect.right +
                ", b - " + rect.bottom +
                ", iL - " + mLeft +
                ", iT - " + mTop +
                ", iR - " + mRight +
                ", iB - " + mBottom +
                ", dt - " + dateTime + "}]";
    }
}
