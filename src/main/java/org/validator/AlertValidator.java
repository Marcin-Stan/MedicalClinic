package org.validator;

import javafx.scene.control.Alert;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertValidator {

    static Alert alert = new Alert(Alert.AlertType.ERROR);

    public static void printALert(String title, String headerText, String contentText, Alert.AlertType alertType) {
        alert.setAlertType(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();

    }

    public static String erroToString(Exception e ){
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }


}
