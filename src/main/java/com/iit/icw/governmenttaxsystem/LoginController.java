package com.iit.icw.governmenttaxsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private Rectangle bgRectangle;

    @FXML
    private Button loginBtn;

    @FXML
    protected void handleLoginButton() throws IOException {
        Stage previousStage = (Stage) bgRectangle.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard-view.fxml")));
        previousStage.setScene(new Scene(root, 1280, 700));
        previousStage.setTitle("Dashboard | Government Tax Department System");
        previousStage.centerOnScreen();
        previousStage.show();
    }
}