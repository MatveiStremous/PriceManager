package com.example.pricemanager.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.pricemanager.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registrationButton;

    @FXML
    void initialize() {

        registrationButton.setOnAction(actionEvent -> {
            Service.changeScene(registrationButton, "registration.fxml");
        });
    }

}
