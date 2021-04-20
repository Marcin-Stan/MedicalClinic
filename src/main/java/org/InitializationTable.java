package org;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.table.Manage;

import java.util.List;

public class InitializationTable<T>{

    public void fillTable(TableView<T> tableView, List<T> entityList)  {

        for(int i = 0; i<entityList.size(); i++){
            tableView.getItems().addAll(entityList.get(i));
        }

    }

    public void addButtonToTable(TableView<T> tableView, final Manage<T> manage, String nameWindow, StackPane stackPane) {
        TableColumn<T, Void> colBtn = new TableColumn("");

        Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory = new Callback<>() {

            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                final TableCell<T, Void> cell = new TableCell<>() {

                    private final JFXButton btn = new JFXButton("Zarządzaj");
                    private Manage<T> manage1 = manage;

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            T entity = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource(nameWindow));
                                Scene scene = new Scene(fxmlLoader.load());

                                manage1 = fxmlLoader.getController();
                                manage1.setParentStackPane(stackPane);
                                manage1.initData(entity);

                                Stage stage = new Stage();
                                stage.setTitle("Zarządzanie");
                                stage.setScene(scene);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableView.getColumns().add(colBtn);

    }





}
