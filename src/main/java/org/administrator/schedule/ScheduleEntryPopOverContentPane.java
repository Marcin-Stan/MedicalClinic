package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.popover.EntryHeaderView;
import com.calendarfx.view.popover.PopOverContentPane;
import com.calendarfx.view.popover.PopOverTitledPane;
import javafx.collections.ObservableList;

import java.awt.*;
import java.util.List;

public class ScheduleEntryPopOverContentPane extends PopOverContentPane {

    public ScheduleEntryPopOverContentPane(Entry entry, List<Calendar> allCalendars){
        //EntryHeaderView header = new EntryHeaderView(entry,allCalendars);
        EntryHeaderView header = new EntryHeaderView(entry, allCalendars);
        ScheduleEntryDetailsView details = new ScheduleEntryDetailsView(entry);
        PopOverTitledPane detailsPane = new PopOverTitledPane("Details", details);
        getPanes().add(detailsPane);

        setExpandedPane(detailsPane);
        setHeader(header);
    }
}
