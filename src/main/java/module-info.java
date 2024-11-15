module com.example.javacapturecard {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires org.bytedeco.javacv;
    requires java.desktop;
    requires javafx.swing;
    requires org.bytedeco.ffmpeg;

    opens com.javacapturecard.app to javafx.fxml;
    exports com.javacapturecard.app;
}