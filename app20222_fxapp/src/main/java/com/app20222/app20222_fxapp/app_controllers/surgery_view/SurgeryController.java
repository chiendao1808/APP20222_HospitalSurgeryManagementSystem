package com.app20222.app20222_fxapp.app_controllers.surgery_view;

import com.app20222.app20222_fxapp.app_controllers.ShowScreen;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class SurgeryController {

    @FXML
    private Button createSurgery;
    @FXML
    private TableView<SurgeryGetListDTO> surgeryTable;
    @FXML
    private ScrollPane surgeryTableView;
    // column
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> id;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryCodeColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryPatientNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryResultColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryRoomColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryStatusColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> typeSurgeryColumn;


    public SurgeryController() {
    }

    @FXML
    public void showModal(ActionEvent event) {
        String FXMLPATH = "views/surgery_view/create.fxml";
        try {
            ShowScreen showWindow = new ShowScreen();
            showWindow.Show(FXMLPATH, "Tạo ca phẫu thuật");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
