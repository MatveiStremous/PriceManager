package com.example.pricemanager.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.pricemanager.Service;
import com.example.pricemanager.action.Action;
import com.example.pricemanager.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistrationController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button completeRegistrationButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    void initialize() {
            backButton.setOnAction(actionEvent -> {
                Service.changeScene(backButton, "login.fxml");
            });

            completeRegistrationButton.setOnAction(actionEvent -> {
                client.writeObject(Action.REGISTRATION);
                client.writeObject(new User(loginField.getText(), passwordField.getText()));
            });
    }

}
