package org.administrator.schedule;

public class InsertEntryTask extends ScheduleTask<ScheduleEntry>{

    private final ScheduleEntry entry;
    private final ScheduleCalendar calendar;

    public InsertEntryTask(ScheduleEntry entry, ScheduleCalendar calendar) {
        this.entry = entry;
        this.calendar = calendar;
        this.logItem.setCalendar(calendar.getName());
        this.logItem.setDescription(getDescription());
    }

    @Override
    public ActionType getAction() {
        return ActionType.INSERT;
    }

    @Override
    public String getDescription() {
        return "Insert " + entry;
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        calendar.addEntries(entry);
    }

    @Override
    protected ScheduleEntry call() throws Exception {
        //Tu będziesz mógł zrobić zapis do bazy
        System.out.println(entry.getId());
        //return this.entry;
        return null;
    }
}
