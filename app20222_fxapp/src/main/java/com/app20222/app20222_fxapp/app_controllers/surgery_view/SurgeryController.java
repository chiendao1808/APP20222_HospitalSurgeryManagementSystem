package com.app20222.app20222_fxapp.app_controllers.surgery_view;

import com.app20222.app20222_fxapp.app_controllers.ShowScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class SurgeryController {


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
