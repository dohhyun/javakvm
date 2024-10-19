module com.example.javacapturecard {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens javacapturecard.app to javafx.fxml;
    exports javacapturecard.app;
}