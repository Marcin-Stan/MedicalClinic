package org;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardAdministratorController implements Initializable {

    @FXML
    JFXButton serviceButton;

    @FXML
    StackPane stackPanedashboard;
    @FXML
    public JFXButton homeScreenButton,
            usersButton,myAccountButton,
            logoutButton,
            patientButton;
    @FXML
    public StackPane stackPaneinside;

    @FXML
    Label welcomeLabel;

    @FXML
    ImageView userInfo;

    public StackPane getStackPaneinside() {
        return stackPaneinside;
    }

    public void setStackPaneinside(StackPane stackPaneinside) {
        this.stackPaneinside = stackPaneinside;
    }
    int temp = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userInfo.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if(temp==0) {
                logoutButton.setDisable(false);
                logoutButton.setVisible(true);

                myAccountButton.setDisable(false);
                myAccountButton.setVisible(true);

                temp =1;
            }else {
                logoutButton.setDisable(true);
                logoutButton.setVisible(false);

                myAccountButton.setDisable(true);
                myAccountButton.setVisible(false);

                temp = 0;
            }
            event.consume();
        });

        homeScreenButton.setDisableVisualFocus(true);
    }

    @FXML
    private void openWindowUsers() throws IOException {
        StackPane pane =  FXMLLoader.load((getClass().getResource("administrator/employee/Employee.fxml")));
        stackPaneinside.getChildren().add(pane);

        usersButton.setTextFill(Color.web("#5fa1fc"));

        homeScreenButton.setTextFill(Color.web("#576271"));
        patientButton.setTextFill(Color.web("#576271"));
        serviceButton.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openWindowPatients() throws IOException{
        StackPane pane =  FXMLLoader.load((getClass().getResource("administrator/patient/Patient.fxml")));
        stackPaneinside.getChildren().add(pane);

        patientButton.setTextFill(Color.web("#5fa1fc"));

        usersButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#576271"));
        serviceButton.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openWindowService() throws IOException{
        StackPane pane =  FXMLLoader.load((getClass().getResource("administrator/service/Service.fxml")));
        stackPaneinside.getChildren().add(pane);

        serviceButton.setTextFill(Color.web("#5fa1fc"));

        homeScreenButton.setTextFill(Color.web("#576271"));
        patientButton.setTextFill(Color.web("#576271"));
        usersButton.setTextFill(Color.web("#576271"));
    }



    @FXML
    private void logout(){

        Stage stage;
        stage = (Stage) usersButton.getScene().getWindow();
        stage.close();

        Parent root = null;
        try {
            root = FXMLLoader.load((getClass().getResource("loginWindow/login.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene  = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true );
        stage.show();

    }

}
