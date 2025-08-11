module com.example.projectneitor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens controller to javafx.fxml;
    opens Model to javafx.fxml;
    opens Application to javafx.fxml;

    exports controller;
    exports Model;
    exports Application;
    exports datastore;
    opens datastore to javafx.fxml;
}
