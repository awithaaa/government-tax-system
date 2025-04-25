package com.iit.icw.governmenttaxsystem;

import com.iit.icw.governmenttaxsystem.dto.TableDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TaxFileController implements Initializable {

    private boolean fileImported = false;

    @FXML
    private Button browserBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private TextField browserField;

    @FXML
    private Text fileName;

    @FXML
    private Text fileType;

    @FXML
    private Text totalLines;

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
    protected void handleNextButton() throws IOException {
        if (fileImported) {
            Stage previousStage = (Stage) browserField.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("checksum-view.fxml")));
            previousStage.setScene(new Scene(root, 1280, 700));
            previousStage.setTitle("Checksum | Government Tax Department System");
            previousStage.centerOnScreen();
            previousStage.show();
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("File Information");
            dialog.setContentText("File has not been imported!");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();

//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("File Import");
//            alert.setContentText("File has not been imported!");
//            alert.showAndWait();
        }
    }

    @FXML
    private void handleChooseFile(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        Stage stage = (Stage) browserBtn.getScene().getWindow();

        // Show open file dialog
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            fileImported = true;
            browserField.setText(file.getAbsolutePath());
            fileName.setText("File Name : " + file.getName());
            String extension = "";
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i + 1);
            }
            fileType.setText("File Type : " + extension);
            initialData(file);
        }
    }

    private void initialData(File file) {
        ObservableList<TableDto> loadedData = FXCollections.observableArrayList();
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
            totalLines.setText("Total Lines : " + String.valueOf(lineTotal));
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
    }
}
