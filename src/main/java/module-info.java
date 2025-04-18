module com.iit.icw.governmenttaxsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.iit.icw.governmenttaxsystem to javafx.fxml;
    exports com.iit.icw.governmenttaxsystem;
}