package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;

import java.util.ArrayList;
import java.util.List;

    public final class LoadAllCalendarsTask extends ScheduleTask<List<ScheduleCalendar>> {

        CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
        List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);
        List<Calendar> calendarList = new ArrayList<>();


        private final ScheduleAccount account;

        public LoadAllCalendarsTask(ScheduleAccount account) {
            super();
            this.account = account;
        }

        @Override
        public ActionType getAction() {
            return ActionType.LOAD;
        }

        @Override
        public String getDescription() {
            return "Loading all calendars";
        }

        @Override
        protected List<ScheduleCalendar> call() throws Exception {
            return null;
        }

        @Override
        protected void succeeded() {
            super.succeeded();

            for(int i=0;i<departmentList.size();i++){
                calendarList.add(new Calendar(departmentList.get(i).getName()));
                calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
                calendarList.get(i).setStyle(Calendar.Style.valueOf("STYLE"+String.valueOf(1+i)));
            }

            account.getCalendars().setAll(calendarList);
        }
}
