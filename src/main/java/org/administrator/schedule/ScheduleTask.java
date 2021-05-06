package org.administrator.schedule;

import javafx.concurrent.Task;

import java.time.LocalDateTime;

public abstract class ScheduleTask<V> extends Task<V> {

    final LogItem logItem;

    protected ScheduleTask(){
        super();
        logItem = new LogItem();
        logItem.setTime(LocalDateTime.now());
        logItem.setStatus(StatusType.PENDING);
        logItem.setDescription(getDescription());
        logItem.setAction(getAction());
    }


    public LogItem getLogItem() {
        return logItem;
    }

    public abstract ActionType getAction();

    public abstract String getDescription();

    @Override
    protected void failed() {
        logItem.setStatus(StatusType.FAILED);
        logItem.setException(getException());
    }

    @Override
    protected void cancelled() {
        logItem.setStatus(StatusType.CANCELLED);
    }

    @Override
    protected void succeeded() {
        logItem.setStatus(StatusType.SUCCEEDED);
    }
}
