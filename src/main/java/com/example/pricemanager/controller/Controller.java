package com.example.pricemanager.controller;

import com.example.pricemanager.Service;
import com.example.pricemanager.connection.Client;

public interface Controller {
    Client client = Service.getClient("127.0.0.1", 8000);
}
