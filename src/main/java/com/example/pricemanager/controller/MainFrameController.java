package com.example.pricemanager.controller;

import com.example.pricemanager.entity.User;
import com.example.pricemanager.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController implements Controller{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button adminButton;

    @FXML
    private Button generalButton;

    @FXML
    void initialize() {
        Controller.updateUserRole();
        if(user.getUserRole().equals(User.UserRole.USER_ROLE)) {
            adminButton.setVisible(false);
        }
        System.out.println(Controller.user.getId());
    }
}
