package com.example.pricemanager.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.service.LoginService;
import com.example.pricemanager.service.Service;
import javafx.event.ActionEvent;
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

    LoginService loginService = new LoginService();

    @FXML
    void initialize() {

    }

    @FXML
    void onClickEnterButton(ActionEvent event) {
        client.writeObject(Action.LOGIN);
        client.writeObject(new User(loginField.getText(), passwordField.getText()));
        user.setLogin(loginField.getText());
        if(loginService.login((Status) client.readObject())){
            Service.changeScene(registrationButton, "mainFrame.fxml");
        }
    }

    @FXML
    void onClickRegistrationButton(ActionEvent event) {
        Service.changeScene(registrationButton, "registration.fxml");
    }
}
