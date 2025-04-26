package com.iit.icw.governmenttaxsystem;

import com.iit.icw.governmenttaxsystem.dto.ProfitTableDto;
import com.iit.icw.governmenttaxsystem.dto.TableDto;
import com.iit.icw.governmenttaxsystem.utils.TaxCalculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaxCalController implements Initializable {

    private final TaxCalculator taxCalculator = new TaxCalculator();
    private ObservableList<TableDto> loadedData = FXCollections.observableArrayList();
    private ObservableList<ProfitTableDto> loadedProfitData = FXCollections.observableArrayList();

    double loss = 0;
    double profit = 0;
    double tax = 0;


    @FXML
    private TextField profitTextField;

    @FXML
    private TextField lossTextField;

    @FXML
    private TextField taxRateTextField;

    @FXML
    private TextField finalTaxTextField;

    @FXML
    private TableColumn<ProfitTableDto, String> billId;

    @FXML
    private TableColumn<ProfitTableDto, String> checkSum;

    @FXML
    private TableColumn<ProfitTableDto, String> discount;

    @FXML
    private TableColumn<ProfitTableDto, String> intPrice;

    @FXML
    private TableColumn<ProfitTableDto, String> itemCode;

    @FXML
    private TableColumn<ProfitTableDto, String> lineId;

    @FXML
    private TableColumn<ProfitTableDto, String> lineTotal;

    @FXML
    private TableColumn<ProfitTableDto, String> qty;

    @FXML
    private TableColumn<ProfitTableDto, String> salePrice;

    @FXML
    private TableColumn<ProfitTableDto, String> profitAmount;

    @FXML
    private TableColumn<ProfitTableDto, String> profitType;

    @FXML
    private TableView<ProfitTableDto> table;

    @FXML
    protected void handleDeleteZeroProfit() {
        ObservableList<ProfitTableDto> tempLoadedProfitData = FXCollections.observableArrayList();
        for (ProfitTableDto r : loadedProfitData) {
            if (Double.parseDouble(r.getProfitAmount()) != 0) {
                tempLoadedProfitData.add(r);
            }
        }
        loadedProfitData = tempLoadedProfitData;
        table.setItems(loadedProfitData);
    }

    @FXML
    protected void handleCalculateTax() {
        calculateTax(loadedProfitData);
    }

    @FXML
    public void handleGenerateTaxReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(table.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("ID,ItemCode,Internal Price,Discount,Sell Price,Quantity,Checksum,Status,Profit\n");

                for (ProfitTableDto t : loadedProfitData) {
                    String line = String.join(",",
                            t.getLineId(),
                            t.getItemCode(),
                            t.getIntPrice(),
                            t.getDiscount(),
                            t.getSalePrice(),
                            t.getQty(),
                            t.getCheckSum(),
                            t.getProfitType(),
                            t.getProfitAmount()
                    );
                    writer.write(line + "\n");
                }
                String profitLine = String.join(",", "Profit :", String.valueOf(profit), "", "", "", "", "", "", "");
                writer.write("\n" + profitLine + "\n");

                String lossLine = String.join(",", "Loss :", String.valueOf(loss), "", "", "", "", "", "", "");
                writer.write(lossLine + "\n");

                String taxRateLine = String.join(",", "Profit :", taxRateTextField.getText(), "", "", "", "", "", "", "");
                writer.write(taxRateLine + "\n");

                String taxLine = String.join(",", "Profit :", String.valueOf(tax), "", "", "", "", "", "", "");
                writer.write(taxLine + "\n");

                new Alert(Alert.AlertType.INFORMATION, "CSV exported successfully!").showAndWait();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving CSV: " + e.getMessage()).showAndWait();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        billId.setCellValueFactory(cellData -> cellData.getValue().billIdProperty());
        checkSum.setCellValueFactory(cellData -> cellData.getValue().checkSumProperty());
        discount.setCellValueFactory(cellData -> cellData.getValue().discountProperty());
        intPrice.setCellValueFactory(cellData -> cellData.getValue().intPriceProperty());
        itemCode.setCellValueFactory(cellData -> cellData.getValue().itemCodeProperty());
        lineId.setCellValueFactory(cellData -> cellData.getValue().lineIdProperty());
        lineTotal.setCellValueFactory(cellData -> cellData.getValue().lineTotalProperty());
        qty.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        salePrice.setCellValueFactory(cellData -> cellData.getValue().salePriceProperty());
        profitAmount.setCellValueFactory(cellData -> cellData.getValue().profitAmountProperty());
        profitType.setCellValueFactory(cellData -> cellData.getValue().profitTypeProperty());
    }

    private void initialData(ObservableList<TableDto> loadedData) {
        for (TableDto r: loadedData) {
            String profitT = "Profit";
            double profit = taxCalculator.calculateProfit(r.getSalePrice(), r.getIntPrice(), r.getDiscount(), r.getQty());
//            double profit = (Double.parseDouble(r.getSalePrice()) * Integer.parseInt(r.getQty())) - Double.parseDouble(r.getDiscount()) - (Double.parseDouble(r.getIntPrice()) * Integer.parseInt(r.getQty()));
            if (profit == 0) {
                profitT = "Zero";
            } else if (profit < 0) {
                profitT = "Loss";
            }
            ProfitTableDto ptd = new ProfitTableDto(
                    r.getLineId(),
                    r.getItemCode(),
                    r.getIntPrice(),
                    r.getDiscount(),
                    r.getSalePrice(),
                    r.getQty(),
                    r.getBillId(),
                    r.getLineTotal(),
                    r.getCheckSum(),
                    String.valueOf(profit),
                    profitT
            );
            loadedProfitData.add(ptd);
        }
        table.setItems(loadedProfitData);
        calculateTax(loadedProfitData);

    }

    private void calculateTax(ObservableList<ProfitTableDto> loadedData) {
        loss = 0;
        profit = 0;
        tax = 0;
        for (ProfitTableDto r: loadedData) {
            if (r.getProfitType().equals("Loss")) {
                loss = loss + Math.abs(Double.parseDouble(r.getProfitAmount()));
            } else {
                profit = profit + Double.parseDouble(r.getProfitAmount());
            }
        }
        lossTextField.setText(String.valueOf(loss));
        profitTextField.setText(String.valueOf(profit));
        if (taxRateTextField.getText() == "") {
            tax = taxCalculator.calculateTax(profit, loss, 0);
        } else {
            tax = taxCalculator.calculateTax(profit, loss, Double.parseDouble(taxRateTextField.getText()));
        }
        finalTaxTextField.setText(String.valueOf(tax));
    }

    public void setLoadedData(ObservableList<TableDto> loadedData) {
        this.loadedData = loadedData;
        initialData(loadedData);
    }
}
