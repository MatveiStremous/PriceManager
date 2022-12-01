package com.example.pricemanager.controller;

import com.example.pricemanager.entity.PriceCalculation;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Scanner;

public class PriceCalculatorController implements Controller {
    @FXML
    private TextField averageCostField;

    @FXML
    private Button calculateButton;

    @FXML
    private TextField increasePercField;

    @FXML
    private Text resultField;

    @FXML
    private TextField taxPercField;

    @FXML
    void initialize() {

    }

    @FXML
    void onClickCalculateButton() {
        if(isInputDataCorrect()) {
            client.writeObject(Action.ADD_NEW_PRICE_CALCULATION);
            client.writeObject(getPriceCalculationFromInputData());
            resultField.setText(String.valueOf(client.readObject()));
        }
    }

    private PriceCalculation getPriceCalculationFromInputData() {
        PriceCalculation priceCalculation = new PriceCalculation();
        priceCalculation.setAverageCost(!averageCostField.getText().equals("") ? Double.parseDouble(averageCostField.getText().replace(",", ".")) : 0f);
        priceCalculation.setTaxPerc(!taxPercField.getText().equals("") ? Float.parseFloat(taxPercField.getText().replace(",", ".")) : 0f);
        priceCalculation.setIncreasePerc(!increasePercField.getText().equals("") ? Float.parseFloat(increasePercField.getText().replace(",", ".")) : 0f);
        priceCalculation.setUserId(user.getId());

        return priceCalculation;
    }

    private boolean isInputDataCorrect() {
        boolean flag = true;
        if (!new Scanner(averageCostField.getText().replace(".", ",")).hasNextDouble()) {
            averageCostField.setText("1000.0");
            flag = false;
            Service.showAlert("Введены некорректные данные. Используйте вещественное (либо целое) число для этого поля.");
        }
        if (!new Scanner(taxPercField.getText().replace(".", ",")).hasNextDouble()) {
            taxPercField.setText("1000.0");
            flag = false;
            Service.showAlert("Введены некорректные данные. Используйте вещественное (либо целое) число для этого поля.");
        }
        if (!new Scanner(increasePercField.getText().replace(".", ",")).hasNextDouble()) {
            increasePercField.setText("1000.0");
            flag = false;
            Service.showAlert("Введены некорректные данные. Используйте вещественное (либо целое) число для этого поля.");
        }
        return flag;
    }
}
