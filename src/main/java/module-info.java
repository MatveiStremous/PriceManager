module com.example.pricemanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pricemanager to javafx.fxml;
    exports com.example.pricemanager;
    exports com.example.pricemanager.controller;
    opens com.example.pricemanager.controller to javafx.fxml;
}