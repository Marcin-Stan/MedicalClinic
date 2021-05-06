package org.administrator.schedule;

import com.calendarfx.model.Calendar;

public class DeleteEntryTask extends ScheduleTask<Boolean>{

    private final ScheduleEntry entry;
    private final ScheduleCalendar calendar;

    public DeleteEntryTask(ScheduleEntry entry, ScheduleCalendar calendar) {
        this.entry = entry;
        this.calendar = calendar;
        this.logItem.setCalendar(calendar.getName());
        this.logItem.setDescription(getDescription());
    }

    @Override
    public ActionType getAction() {
        return ActionType.DELETE;
    }

    @Override
    public String getDescription() {
        return "Delete " + entry;
    }

    @Override
    protected Boolean call() throws Exception {
        System.out.println("usuwanko");
        return true;
    }
}
