package com.example.pricemanager.service;

import com.example.pricemanager.Application;
import com.example.pricemanager.connection.Client;
import com.example.pricemanager.message.Action;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class Service {
    public static void changeScene(Button button, String fxmlFile) {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlFile));
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static Client getClient(String host, int port){
        try {
            return new Client(new Socket(host, port));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public static void showAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    public static void changeTab(AnchorPane wrapper, String fxmlFile){
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource(fxmlFile)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        wrapper.getChildren().removeAll();
        wrapper.getChildren().setAll(fxml);
    }
}
