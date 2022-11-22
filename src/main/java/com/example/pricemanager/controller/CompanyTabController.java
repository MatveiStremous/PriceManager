package com.example.pricemanager.controller;

import com.example.pricemanager.entity.Company;
import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.service.CompanyService;
import com.example.pricemanager.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CompanyTabController implements Controller {
    private ObservableList<Company> tableData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button adminButton;

    @FXML
    private Button chooseCurrentCompanyButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<?, ?> col_amount_of_products;

    @FXML
    private TableColumn<?, ?> col_balance;

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private Button companyTabButton;

    @FXML
    private TextArea currentCompanyArea;

    @FXML
    private Button deleteButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField name_field;

    @FXML
    private TextField balance_field;

    @FXML
    private TableView<Company> table;

    @FXML
    private Button updateButton;

    private CompanyService companyService = new CompanyService();

    @FXML
    void initialize() {
        Controller.updateUserRole();
        if (user.getUserRole().equals(User.UserRole.USER_ROLE)) {
            adminButton.setVisible(false);
        }
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        col_amount_of_products.setCellValueFactory(new PropertyValueFactory<>("amountOfProducts"));

        loadDataFromDB();
    }

    @FXML
    void onClickAddButton(ActionEvent event) {

        if (isInputDataCorrect()) {
            Company company = getCompanyFromInputData();
            company.setUserId(user.getId());
            client.writeObject(Action.ADD_NEW_COMPANY);
            client.writeObject(company);
            if (companyService.isCompanyAdded((Status) client.readObject())) {
                Service.showAlert("Вы успешно добавили новую компанию. Переходите к добавлению товаров.");
            }

            loadDataFromDB();
        }

    }

    @FXML
    void onClickChooseCurrentCompanyButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Company company = table.getSelectionModel().getSelectedItem();
            currentCompany.setName(company.getName());
            currentCompany.setId(company.getId());
            currentCompany.setUserId(user.getId());
            currentCompany.setBalance(company.getBalance());
            currentCompany.setAmountOfProducts(company.getAmountOfProducts());
            currentCompanyArea.setText(currentCompany.getName());
        } else {
            Service.showAlert("Для выполнения этой операции выберите компанию из таблицы.");
        }
    }

    @FXML
    void onClickClearButton(ActionEvent event) {
        name_field.clear();
        balance_field.clear();
    }

    @FXML
    void onClickCompanyTabButton(ActionEvent event) {

    }

    @FXML
    void onClickDeleteButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            client.writeObject(Action.DELETE_COMPANY);
            client.writeObject(table.getSelectionModel().getSelectedItem().getId());
            Service.showAlert("Вы успешно удалили компанию.");
        } else {
            Service.showAlert("Для выполнения этой операции выберите компанию из таблицы.");
        }

        loadDataFromDB();
    }

    @FXML
    void onClickUpdateButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (isInputDataCorrect()) {
                Company company = getCompanyFromInputData();
                company.setId(table.getSelectionModel().getSelectedItem().getId());
                client.writeObject(Action.UPDATE_COMPANY);
                client.writeObject(company);
                if (companyService.isCompanyAdded((Status) client.readObject())) {
                    Service.showAlert("Вы успешно обновили данные о компании.");
                }
                loadDataFromDB();
            }
        } else {
            Service.showAlert("Для выполнения этой операции выберите компанию из таблицы, а затем отредактируйте необходимые поля.");
        }
    }

    @FXML
    void onSelectOneRecord(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Company selectedCompany = table.getSelectionModel().getSelectedItem();
            name_field.setText(selectedCompany.getName());
            balance_field.setText(String.valueOf(selectedCompany.getBalance()));
        }
    }

    private void loadDataFromDB() {
        tableData.clear();
        client.writeObject(Action.GET_ALL_USER_COMPANIES);
        client.writeObject(user.getId());
        List<Company> tempList = (List<Company>) client.readObject();
        tableData = FXCollections.observableArrayList(tempList);
        table.setItems(tableData);
    }

    @FXML
    void onClickLogoutButton(ActionEvent event) {

    }

    private Company getCompanyFromInputData() {
        Company company = new Company();
        company.setName(!name_field.getText().equals("") ? name_field.getText() : "");
        company.setBalance(!balance_field.getText().equals("") ? Float.parseFloat(balance_field.getText().replace(",", ".")) : 0f);
        return company;
    }

    private boolean isInputDataCorrect() {
        boolean flag = true;
        if (!new Scanner(balance_field.getText().replace(".", ",")).hasNextFloat()) {
            balance_field.setText("1000.0");
            flag = false;
            Service.showAlert("Введены некорректные данные. Используйте вещественное (либо целое) число для баланса.");
        }
        if (name_field.getText().trim().equals("")) {
            name_field.setText("Интеграл");
            flag = false;
            Service.showAlert("Имя компании не может быть пустым. Исправьте это.");
        }
        return flag;
    }
}
