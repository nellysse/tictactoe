module org.example.xose {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.xose to javafx.fxml;
    exports org.example.xose;
}