package com.example.pricemanager.controller;

import com.example.pricemanager.entity.Product;
import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Action;
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

public class ProductTabController implements Controller {
    private ObservableList<Product> tableData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button adminButton;

    @FXML
    private Button calculatorTabButton;

    @FXML
    private Button chooseCurrentProductButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<?, ?> col_amount;

    @FXML
    private TableColumn<?, ?> col_average_cost;

    @FXML
    private TableColumn<?, ?> col_average_selling_price;

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private Button companyTabButton;

    @FXML
    private TextArea currentProductArea;

    @FXML
    private Button deleteButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField name_field;

    @FXML
    private Button productTabButton;

    @FXML
    private Button productionTabButton;

    @FXML
    private Button saleTabButton;

    @FXML
    private TableView<Product> table;

    @FXML
    private Button updateButton;

    @FXML
    void initialize() {
        Controller.updateUserRole();
        if (user.getUserRole().equals(User.UserRole.USER_ROLE)) {
            adminButton.setVisible(false);
        }
        if(currentProduct.getId() != 0){
            currentProductArea.setText(currentProduct.getName());
        }
        if (currentCompany.getId() == 0) {
            Service.showAlert("Вы должны выбрать текущую компанию для работы с товарами");
            Service.changeScene(clearButton, "companyTab.fxml");
        } else {
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            col_average_cost.setCellValueFactory(new PropertyValueFactory<>("averageCost"));
            col_average_selling_price.setCellValueFactory(new PropertyValueFactory<>("averageSellingPrice"));

            loadDataFromDB();
        }
    }

    @FXML
    void onClickAddButton(ActionEvent event) {
        if (isInputDataCorrect()) {
            Product product = getProductFromInputData();
            product.setCompanyId(currentCompany.getId());
            client.writeObject(Action.ADD_NEW_PRODUCT);
            client.writeObject(product);
            Service.showAlert("Вы успешно добавили новую компанию. Переходите к добавлению товаров.");

            loadDataFromDB();
        }
    }

    @FXML
    void onClickCalculatorTabButton(ActionEvent event) {

    }

    @FXML
    void onClickChooseCurrentProductButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Product product = table.getSelectionModel().getSelectedItem();
            currentProduct.setName(product.getName());
            currentProduct.setId(product.getId());
            currentProduct.setCompanyId(currentCompany.getId());
            currentProduct.setAmount(product.getAmount());
            currentProduct.setAverageCost(product.getAverageCost());
            currentProduct.setAverageSellingPrice(product.getAverageSellingPrice());
            currentProductArea.setText(currentProduct.getName());
        } else {
            Service.showAlert("Для выполнения этой операции выберите компанию из таблицы.");
        }
    }

    @FXML
    void onClickClearButton(ActionEvent event) {
        name_field.clear();
    }

    @FXML
    void onClickCompanyTabButton(ActionEvent event) {
        Service.changeScene(productTabButton, "companyTab.fxml");
    }

    @FXML
    void onClickDeleteButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            client.writeObject(Action.DELETE_PRODUCT);
            client.writeObject(table.getSelectionModel().getSelectedItem().getId());
            Service.showAlert("Вы успешно удалили продукт.");
        } else {
            Service.showAlert("Для выполнения этой операции выберите продукт из таблицы.");
        }

        loadDataFromDB();
    }

    @FXML
    void onClickLogoutButton(ActionEvent event) {

    }

    @FXML
    void onClickProductTabButton(ActionEvent event) {

    }

    @FXML
    void onClickProductionTabButton(ActionEvent event) {

    }

    @FXML
    void onClickSaleTabButton(ActionEvent event) {

    }

    @FXML
    void onClickUpdateButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (isInputDataCorrect()) {
                Product product = getProductFromInputData();
                product.setId(table.getSelectionModel().getSelectedItem().getId());
                client.writeObject(Action.UPDATE_PRODUCT);
                client.writeObject(product);
                Service.showAlert("Вы успешно обновили данные о продукте.");
                loadDataFromDB();
            }
        } else {
            Service.showAlert("Для выполнения этой операции выберите компанию из таблицы, а затем отредактируйте необходимые поля.");
        }
    }

    @FXML
    void onSelectOneRecord(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Product selectedProduct = table.getSelectionModel().getSelectedItem();
            name_field.setText(selectedProduct.getName());
        }
    }

    private void loadDataFromDB() {
        tableData.clear();
        client.writeObject(Action.GET_ALL_COMPANY_PRODUCTS);
        client.writeObject(currentCompany.getId());
        List<Product> tempList = (List<Product>) client.readObject();
        tableData = FXCollections.observableArrayList(tempList);
        table.setItems(tableData);
    }

    private Product getProductFromInputData() {
        Product product = new Product();
        product.setName(!name_field.getText().equals("") ? name_field.getText() : "");
        return product;
    }

    private boolean isInputDataCorrect() {
        boolean flag = true;
        if (name_field.getText().trim().equals("")) {
            name_field.setText("Цемент");
            flag = false;
            Service.showAlert("Наименование товара не может быть пустым. Исправьте это.");
        }
        return flag;
    }
}
