package com.example.pricemanager.controller;

import com.example.pricemanager.connection.Client;
import com.example.pricemanager.entity.Company;
import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.service.Service;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public interface Controller {
    Client client = Service.getClient("127.0.0.1", 8000);
    User user = new User();
    Company currentCompany = new Company();

    EventHandler<WindowEvent> closeEventHandler = windowEvent -> {
        client.writeObject(Action.EXIT);
        client.disConnect();
        System.exit(0);
    };

    static void updateUserRole(){
        client.writeObject(Action.CHECK_ROLE);
        client.writeObject(user.getLogin());
        user.setUserRole((User.UserRole) client.readObject());
    }
}
