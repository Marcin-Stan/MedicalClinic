package org.administrator.schedule;

import impl.com.calendarfx.view.util.Util;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class ScheduleTaskExecutor {

    private static ScheduleTaskExecutor instance;

    public static ScheduleTaskExecutor getInstance() {
        if (instance == null) {
            instance = new ScheduleTaskExecutor();
        }
        return instance;
    }

    private final IntegerProperty pendingTasks = new SimpleIntegerProperty(this, "pendingTasks", 0);

    private final ExecutorService executor = Executors.newFixedThreadPool(5, new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("Google-Calendar-Executor-%d")
            .setPriority(Thread.MIN_PRIORITY)
            .build());

    private void updatePendingTasks(ScheduleTask<?> task) {
        Util.runInFXThread(() -> {
            pendingTasks.set(pendingTasks.get() + 1);
            EventHandler<WorkerStateEvent> handler = evt -> pendingTasks.set(pendingTasks.get() - 1);
            task.setOnFailed(handler);
            task.setOnSucceeded(handler);
        });
    }

    public final void execute(ScheduleTask<?> task) {
        updatePendingTasks(task);
        executor.submit(task);
    }

}
