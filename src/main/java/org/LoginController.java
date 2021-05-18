package org;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.database.employee.Employee;
import org.employee.EmployeeType;
import org.scheduleCalendar.Schedule;
import org.visitCalendar.Visit;

import java.io.IOException;

public class LoginController {
    @FXML
    JFXTextField loginField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    Pane wrongPassPane;
    @FXML
    Button wrongPassButton;
    @FXML
    AnchorPane loginWindowPane;
    @FXML
    JFXButton loginButton;
    @FXML
    private void closePane(){
        wrongPassPane.setVisible(false);
        wrongPassPane.setDisable(true);
    }

    @FXML
    private void checkPass(){

        if(Employee.checkEmployeeLoginAndPassword(loginField.getText(),passwordField.getText())){
            Stage stage;
            stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            EmployeeType employeeType = Employee.getEmployeeTypeByLogin(loginField.getText());

            switch (employeeType){
                case Pielęgniarka:
                    Stage stage2 = new Stage();
                    Visit visit = new Visit();
                    visit.start(stage2);
                    break;
                case Administrator:
                    Parent root = null;
                    try {
                        root = FXMLLoader.load((getClass().getResource("administrator/Dashboard.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene  = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                    break;
            }

        }else {
            wrongPassPane.setVisible(true);
            wrongPassPane.setDisable(false);
        }
    }
}
