package org.scheduleCalendar;

import com.calendarfx.model.Calendar;
import com.calendarfx.view.popover.EntryHeaderView;
import com.calendarfx.view.popover.EntryPropertiesView;
import com.calendarfx.view.popover.PopOverContentPane;
import com.calendarfx.view.popover.PopOverTitledPane;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ScheduleEntryPopOverContentPane extends PopOverContentPane {

    public ScheduleEntryPopOverContentPane(ScheduleEntry entry, List<Calendar> allCalendars){
        //EntryHeaderView header = new EntryHeaderView(entry,allCalendars);
        requireNonNull(entry);
        EntryHeaderView header = new EntryHeaderView(entry, allCalendars);
        header.getChildren().get(2).setVisible(false);
        ScheduleEntryDetailsView details = new ScheduleEntryDetailsView(entry);
        PopOverTitledPane detailsPane = new PopOverTitledPane("Schedule", details);

        if (Boolean.getBoolean("calendarfx.developer")) {
            EntryPropertiesView properties = new EntryPropertiesView(entry);
            PopOverTitledPane propertiesPane = new PopOverTitledPane("Properties", properties);
            getPanes().addAll(detailsPane, propertiesPane);
        } else {
            getPanes().addAll(detailsPane);
        }

        setExpandedPane(detailsPane);
        setHeader(header);
    }
}
