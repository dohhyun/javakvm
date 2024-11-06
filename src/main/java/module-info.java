module com.example.javacapturecard {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;


    opens com.javacapturecard.app to javafx.fxml;
    exports com.javacapturecard.app;
}