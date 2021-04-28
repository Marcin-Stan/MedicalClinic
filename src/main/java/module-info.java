module org {
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires com.jfoenix;
    requires java.desktop;
    requires javafx.swing;
    requires org.hibernate.validator;
    requires java.validation;
    requires javafx.controls;
    requires com.calendarfx.view;

    opens org to javafx.fxml,org.hibernate.orm.core;
    opens org.database.specialization to org.hibernate.orm.core, javafx.base;
    opens org.database.employee to org.hibernate.orm.core, org.hibernate.validator,javafx.base;
    opens org.database.department to org.hibernate.orm.core, javafx.base,org.hibernate.validator;
    opens org.database.schedule to org.hibernate.orm.core, javafx.base,org.hibernate.validator;
    opens org.database.service to org.hibernate.orm.core, javafx.base,org.hibernate.validator;
    opens org.database.visit to org.hibernate.orm.core, javafx.base,org.hibernate.validator;
    opens org.database.patient to org.hibernate.orm.core, javafx.base,org.hibernate.validator;
    opens org.database.drug to org.hibernate.orm.core, javafx.base,org.hibernate.validator;

    opens org.administrator to org.hibernate.orm.core, javafx.base,javafx.fxml;
    opens org.administrator.employee to org.hibernate.orm.core, javafx.base,javafx.fxml;
    opens org.administrator.patient to org.hibernate.orm.core, javafx.base,javafx.fxml;
    opens org.administrator.service to org.hibernate.orm.core, javafx.base,javafx.fxml;
    opens org.administrator.department to org.hibernate.orm.core, javafx.base,javafx.fxml;
    opens org.administrator.specialization to org.hibernate.orm.core, javafx.base,javafx.fxml;
    opens org.administrator.schedule to org.hibernate.orm.core, javafx.base,javafx.fxml,javafx.graphics;

    exports org;

}