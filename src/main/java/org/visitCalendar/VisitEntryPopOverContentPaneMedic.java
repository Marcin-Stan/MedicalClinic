package org.visitCalendar;

import com.calendarfx.model.Calendar;
import com.calendarfx.view.popover.EntryHeaderView;
import com.calendarfx.view.popover.EntryPropertiesView;
import com.calendarfx.view.popover.PopOverContentPane;
import com.calendarfx.view.popover.PopOverTitledPane;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class VisitEntryPopOverContentPaneMedic extends PopOverContentPane {

    public VisitEntryPopOverContentPaneMedic(VisitEntry entry, List<Calendar> allCalendars){
        //EntryHeaderView header = new EntryHeaderView(entry,allCalendars);
        requireNonNull(entry);
        EntryHeaderView header = new EntryHeaderView(entry, allCalendars);
        header.getChildren().get(2).setVisible(false);
        VisitEntryDetailsViewMedic details = new VisitEntryDetailsViewMedic(entry);
        PopOverTitledPane detailsPane = new PopOverTitledPane("Visit", details);

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
