package com.example.pricemanager.controller;

import com.example.pricemanager.entity.Production;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProductionTabController implements Controller {
    private ObservableList<Production> tableData = FXCollections.observableArrayList();

    @FXML
    private TableView<Production> table;

    @FXML
    private TableColumn<?, ?> col_amount;

    @FXML
    private TableColumn<?, ?> col_date;

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_total_costs;

    @FXML
    private Text currentProductField;

    @FXML
    private DatePicker date_field;

    @FXML
    private TextField amount_field;

    @FXML
    private TextField total_costs_field;

    @FXML
    private Button updateButton;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        currentProductField.setText(currentProduct.getId() + ". \"" + currentProduct.getName() + "\"");

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_total_costs.setCellValueFactory(new PropertyValueFactory<>("totalCosts"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadDataFromDB();

    }

    @FXML
    void onClickAddButton(ActionEvent event) {
        if (isInputDataCorrect()) {
            Production production = getProductionFromInputData();
            production.setProductId(currentProduct.getId());
            client.writeObject(Action.ADD_NEW_PRODUCTION);
            client.writeObject(production);

            loadDataFromDB();
            Service.showAlert("Вы успешно добавили новую партию.");
        }
    }

    @FXML
    void onClickClearButton(ActionEvent event) {
        date_field.setValue(null);
        amount_field.clear();
        total_costs_field.clear();
    }

    @FXML
    void onClickDeleteButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            client.writeObject(Action.DELETE_PRODUCTION);
            client.writeObject(table.getSelectionModel().getSelectedItem().getId());
            loadDataFromDB();
            Service.showAlert("Вы успешно удалили партию.");
        } else {
            Service.showAlert("Для выполнения этой операции выберите партию из таблицы.");
        }
    }

    @FXML
    void onClickUpdateButton(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (isInputDataCorrect()) {
                Production production = getProductionFromInputData();
                production.setId(table.getSelectionModel().getSelectedItem().getId());
                production.setProductId(currentProduct.getId());
                client.writeObject(Action.UPDATE_PRODUCTION);
                client.writeObject(production);

                loadDataFromDB();
                Service.showAlert("Вы успешно обновили данные о партии.");
            }
        } else {
            Service.showAlert("Для выполнения этой операции выберите партию из таблицы, а затем отредактируйте необходимые поля.");
        }
    }

    @FXML
    void onSelectOneRecord(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Production selectedProduction = table.getSelectionModel().getSelectedItem();
            amount_field.setText(String.valueOf(selectedProduction.getAmount()));
            total_costs_field.setText(String.valueOf(selectedProduction.getTotalCosts()));
            date_field.setValue(selectedProduction.getDate());
        }
    }

    private void loadDataFromDB() {
        tableData.clear();
        client.writeObject(Action.GET_ALL_PRODUCT_PRODUCTIONS);
        client.writeObject(currentProduct.getId());
        List<Production> tempList = (List<Production>) client.readObject();
        tableData = FXCollections.observableArrayList(tempList);
        table.setItems(tableData);
    }

    private Production getProductionFromInputData() {
        Production production = new Production();
        production.setAmount(!amount_field.getText().equals("") ? Integer.parseInt(amount_field.getText()) : 1);
        production.setTotalCosts(!total_costs_field.getText().equals("") ? Float.parseFloat(total_costs_field.getText().replace(",", ".")) : 0f);
        production.setDate(date_field != null ? date_field.getValue() : LocalDate.now());
        return production;
    }

    private boolean isInputDataCorrect() {
        boolean flag = true;
        if (!new Scanner(amount_field.getText()).hasNextInt()) {
            amount_field.setText("10");
            flag = false;
            Service.showAlert("Введены некорректные данные. Используйте целое число для количества товаров в партии.");
        }
        if (!new Scanner(total_costs_field.getText().replace(".", ",")).hasNextFloat()) {
            total_costs_field.setText("1000.0");
            flag = false;
            Service.showAlert("Введены некорректные данные. Используйте вещественное (либо целое) число для общих издержек.");
        }
        if (date_field.getValue() == null) {
            date_field.setValue(LocalDate.now());
            flag = false;
            Service.showAlert("Вы не указали дату. Нажмите на календарь рядом с полем для выбора даты.");
        }
        return flag;
    }
}
