module com.example.javacapturecard {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires org.bytedeco.javacv;
    requires java.desktop;
    requires javafx.swing;
    requires org.bytedeco.ffmpeg;

    opens javacapturecard.app to javafx.fxml;
    exports javacapturecard.app;
}