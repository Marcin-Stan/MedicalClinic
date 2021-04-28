package org.table;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.operation.CRUD;

import java.io.IOException;

public interface Manage<T> {

    void initData(T entity);
    void setParentStackPane(StackPane stackPane);

    default void setCloseButton(Button button,String pathToWindow, StackPane stackPane){
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        try {
            StackPane test = FXMLLoader.load(getClass().getResource(pathToWindow));
            stackPane.getChildren().add(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void setDeleteButton(CRUD<T> crud,T entity,
                                 Button button, StackPane stackPane,
                                 String pathWindow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText("Czy na pewno chcesz usunąć?");
        alert.setContentText("Zmiany będą nieodwracalne");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            if(crud.delete(entity)){

                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();

                try {
                    StackPane test = FXMLLoader.load(getClass().getResource(pathWindow));
                    stackPane.getChildren().add(test);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }



}
