package com.example.pricemanager;

import com.example.pricemanager.connection.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Service {

    public static void changeScene(Button button, String fxmlFile) {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Service.class.getResource(fxmlFile));
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
}
