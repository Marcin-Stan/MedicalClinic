package org.administrator.schedule;

import com.calendarfx.view.CalendarView;
import javafx.application.Platform;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarViewTimeUpdateThread extends Thread{

    private static final int TEN_SECONDS = 10000;

    private final CalendarView calendarView;

    public CalendarViewTimeUpdateThread(CalendarView calendarView) {
        super("Google-Calendar-Update Current Time");
        this.calendarView = calendarView;
        setPriority(MIN_PRIORITY);
        setDaemon(true);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            Platform.runLater(() -> {
                calendarView.setToday(LocalDate.now());
                calendarView.setTime(LocalTime.now());
            });

            try {
                sleep(TEN_SECONDS);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }
}
