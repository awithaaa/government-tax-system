package com.iit.icw.governmenttaxsystem;

import com.iit.icw.governmenttaxsystem.dto.TableDto;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TaxCalController {
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
}
