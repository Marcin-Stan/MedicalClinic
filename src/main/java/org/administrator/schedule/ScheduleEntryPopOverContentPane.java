package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.popover.EntryHeaderView;
import com.calendarfx.view.popover.PopOverContentPane;
import com.calendarfx.view.popover.PopOverTitledPane;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ScheduleEntryPopOverContentPane extends PopOverContentPane {

    public ScheduleEntryPopOverContentPane(ScheduleEntry entry, List<Calendar> allCalendars){
        //EntryHeaderView header = new EntryHeaderView(entry,allCalendars);
        requireNonNull(entry);
        EntryHeaderView header = new EntryHeaderView(entry, allCalendars);
        ScheduleEntryDetailsView details = new ScheduleEntryDetailsView(entry);
        PopOverTitledPane detailsPane = new PopOverTitledPane("Essa Wariat", details);
        getPanes().add(detailsPane);

        setExpandedPane(detailsPane);
        setHeader(header);
    }
}
