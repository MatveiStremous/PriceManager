package com.example.pricemanager.controller;

import com.example.pricemanager.entity.User;
import com.example.pricemanager.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainPageController implements Controller {

    @FXML
    private AnchorPane wrapper;

    @FXML
    private Button adminTabButton;

    @FXML
    private Button calculatorTabButton;

    @FXML
    private Button companyTabButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button productTabButton;

    @FXML
    private Button productionTabButton;

    @FXML
    private Button saleTabButton;

    @FXML
    private Button personalTabButton;

    @FXML
    private Button statisticTabButton;

    @FXML
    void initialize() {
        onClickCompanyTabButton();
        if (user.getUserRole().equals(User.UserRole.USER_ROLE)) {
            adminTabButton.setVisible(false);
        }
    }

    @FXML
    void onClickCalculatorTabButton(ActionEvent event) {
        changeTab("calculatorTab.fxml");
    }

    @FXML
    void onClickCompanyTabButton() {
        changeTab("companyTab.fxml");
    }

    @FXML
    void onClickLogoutButton(ActionEvent event) {
        currentCompany.setId(0);
        currentProduct.setId(0);
        user.setId(0);
        Service.changeScene(logoutButton, "login.fxml");
    }

    @FXML
    void onClickProductTabButton(ActionEvent event) {
        if (currentCompany.getId() == 0) {
            Service.showAlert("Вы должны выбрать текущую компанию для работы с товарами");
        } else {
            changeTab("productTab.fxml");
        }
    }

    @FXML
    void onClickProductionTabButton(ActionEvent event) {
        if (currentProduct.getId() == 0) {
            Service.showAlert("Вы должны выбрать текущий товар для работы с производством");
        } else {
            changeTab("productionTab.fxml");
        }
    }

    @FXML
    void onClickSaleTabButton(ActionEvent event) {
        if (currentProduct.getId() == 0) {
            Service.showAlert("Вы должны выбрать текущий товар для работы с продажами");
        } else {
            changeTab("saleTab.fxml");
        }
    }

    @FXML
    void onClickAdminTabButton(ActionEvent event) {
        changeTab("adminTab.fxml");
    }

    @FXML
    void onClickPersonalTabButton(ActionEvent event) {
        changeTab("personalTab.fxml");
    }

    @FXML
    void onClickStatisticTabButton(ActionEvent event) {
        if (currentProduct.getId() == 0) {
            Service.showAlert("Вы должны выбрать текущий товар просмотра графиков");
        } else {
            changeTab("statisticTab.fxml");
        }
    }

    public void changeTab(String fxlFileName) {
        Service.changeTab(wrapper, fxlFileName);
    }
}
