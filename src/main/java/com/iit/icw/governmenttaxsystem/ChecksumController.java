package com.iit.icw.governmenttaxsystem;

import com.iit.icw.governmenttaxsystem.dto.TableDto;
import com.iit.icw.governmenttaxsystem.utils.CheckSum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ChecksumController implements Initializable {
    private File file;
    private int invalidTotal = 0;
    private int validTotal = 0;
    private final ObservableList<TableDto> loadedData = FXCollections.observableArrayList();

    @FXML
    private TextField totalRecordsText;

    @FXML
    private TextField totalValidText;

    @FXML
    private TextField totalInvalidText;

    @FXML
    private TextArea invalidRecordsTextArea;

    @FXML
    private TableColumn<TableDto, String> billId;

    @FXML
    private TableColumn<TableDto, String> checkSum;

    @FXML
    private TableColumn<TableDto, String> discount;

    @FXML
    private TableColumn<TableDto, String> intPrice;

    @FXML
    private TableColumn<TableDto, String> itemCode;

    @FXML
    private TableColumn<TableDto, String> lineId;

    @FXML
    private TableColumn<TableDto, String> lineTotal;

    @FXML
    private TableColumn<TableDto, String> qty;

    @FXML
    private TableColumn<TableDto, String> salePrice;

    @FXML
    private TableView<TableDto> table;

    @FXML
    protected void handleValidation() {
        validTotal = 0;
        invalidTotal = 0;
        CheckSum checkSum = new CheckSum();
        String lines = "Invalid Records : ";
        ArrayList<String> invalidRecordsId = new ArrayList<>();
        for (TableDto r: loadedData) {
            String[] tokens = {r.getLineId(), r.getItemCode(), r.getIntPrice(), r.getDiscount(), r.getSalePrice(), r.getQty(), r.getLineTotal(),};
            int currentChecksumValue = Integer.parseInt(r.getCheckSum());
            int checkSumValue = checkSum.totalChecksum(tokens) ;
            if (currentChecksumValue == checkSumValue) {
                validTotal++;
            } else {
                invalidTotal++;
                invalidRecordsId.add("Line ID "+ r.getLineId() + " : " +  checkSumValue + " -> " + currentChecksumValue);
                lines = (lines + "\n Line ID " + r.getLineId() + " : " +  checkSumValue + " -> " + currentChecksumValue);
            }
        }
        totalInvalidText.setText(String.valueOf(invalidTotal));
        totalValidText.setText(String.valueOf(validTotal));


        invalidRecordsTextArea.setText(lines);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Checksum Validation");
        alert.setHeaderText("Checksum Validation has been done.");
        alert.setContentText("Total valid : "+ validTotal + ", Total Invalid : "+ invalidTotal + " found!");
        alert.showAndWait();
    }

    @FXML
    protected void handleDeleteDataAtSpecificRow(ActionEvent event) {
        TableView.TableViewSelectionModel<TableDto> selectionModel = table.getSelectionModel();
        if (selectionModel.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data deletion");
            alert.setHeaderText("You need to select a row.");
            alert.setContentText("You haven't select a row. Please select the row you want to delete and try again.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you really want to delete this row?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            ObservableList<Integer> list = selectionModel.getSelectedIndices();
            Integer[] selectedIndices = new Integer[list.size()];
            selectedIndices = list.toArray(selectedIndices);
            Arrays.sort(selectedIndices);
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                selectionModel.clearSelection(selectedIndices[i].intValue());
                table.getItems().remove(selectedIndices[i].intValue());
            }
        }

    }

    @FXML
    protected void handleNextButton() throws IOException {
        if (invalidTotal == 0) {
            Stage previousStage = (Stage) totalValidText.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("tax-cal-view.fxml")));
            Parent root = loader.load();
            ChecksumController checksumController = loader.getController();
            checksumController.setFile(file);
            previousStage.setScene(new Scene(root, 1280, 700));
            previousStage.setTitle("Tax Calculation | Government Tax Department System");
            previousStage.centerOnScreen();
            previousStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Action");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you really want to calculate tax with out fixing the invalid records?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                Stage previousStage = (Stage) totalValidText.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("tax-cal-view.fxml")));
                Parent root = loader.load();
                ChecksumController checksumController = loader.getController();
                checksumController.setFile(file);
                previousStage.setScene(new Scene(root, 1280, 700));
                previousStage.setTitle("Tax Calculation | Government Tax Department System");
                previousStage.centerOnScreen();
                previousStage.show();
            }
        }
    }


    public void setFile(File file) {
        this.file = file;
        initialData(this.file);
    }

    private void editData() {
        lineId.setCellFactory(TextFieldTableCell.<TableDto>forTableColumn());
        lineId.setOnEditCommit(event -> {
            TableDto tableRow = event.getTableView().getItems().get(event.getTablePosition().getRow());
            tableRow.setLineId(event.getNewValue());
        });

        itemCode.setCellFactory(TextFieldTableCell.<TableDto>forTableColumn());
        itemCode.setOnEditCommit(event -> {
            TableDto tableRow = event.getTableView().getItems().get(event.getTablePosition().getRow());
            tableRow.setItemCode(event.getNewValue());
        });

        intPrice.setCellFactory(TextFieldTableCell.<TableDto>forTableColumn());
        intPrice.setOnEditCommit(event -> {
            TableDto tableRow = event.getTableView().getItems().get(event.getTablePosition().getRow());
            tableRow.setIntPrice(event.getNewValue());
//            tableRow.setLineTotal(String.valueOf(Integer.parseInt(event.getNewValue()) - Integer.parseInt(tableRow.getDiscount())));
        });

        discount.setCellFactory(TextFieldTableCell.<TableDto>forTableColumn());
        discount.setOnEditCommit(event -> {
            TableDto tableRow = event.getTableView().getItems().get(event.getTablePosition().getRow());
            tableRow.setDiscount(event.getNewValue());
//            tableRow.setLineTotal(String.valueOf(Integer.parseInt(event.getNewValue()) - Integer.parseInt(tableRow.getDiscount())));
        });

        salePrice.setCellFactory(TextFieldTableCell.<TableDto>forTableColumn());
        salePrice.setOnEditCommit(event -> {
            TableDto tableRow = event.getTableView().getItems().get(event.getTablePosition().getRow());
            tableRow.setSalePrice(event.getNewValue());
        });

        qty.setCellFactory(TextFieldTableCell.<TableDto>forTableColumn());
        qty.setOnEditCommit(event -> {
            TableDto tableRow = event.getTableView().getItems().get(event.getTablePosition().getRow());
            tableRow.setQty(event.getNewValue());
        });

        lineTotal.setCellFactory(TextFieldTableCell.<TableDto>forTableColumn());
        lineTotal.setOnEditCommit(event -> {
            TableDto tableRow = event.getTableView().getItems().get(event.getTablePosition().getRow());
            tableRow.setLineTotal(event.getNewValue());
        });
    }

    private void initialData(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int lineTotal = 0;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] tokens = line.split(",", -1);

                if (tokens.length >= 9) {
                    TableDto dto = new TableDto(
                            tokens[0].trim(), // lineId
                            tokens[1].trim(), // itemCode
                            tokens[2].trim(), // intPrice
                            tokens[3].trim(), // discount
                            tokens[4].trim(), // salePrice
                            tokens[5].trim(), // qty
                            tokens[6].trim(), // billId
                            tokens[7].trim(), // lineTotal
                            tokens[8].trim()  // checkSum
                    );
                    loadedData.add(dto);
                    lineTotal += 1;
                }
            }
            table.setItems(loadedData);
            totalRecordsText.setText(String.valueOf(lineTotal));
        } catch (IOException e) {
            e.printStackTrace();
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

        editData();
    }

}
