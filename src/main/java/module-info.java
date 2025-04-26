module com.iit.icw.governmenttaxsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.iit.icw.governmenttaxsystem to javafx.fxml;
    exports com.iit.icw.governmenttaxsystem;
}