package com.javacapturecard.app;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 250;
    public boolean detectConnection = false;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("JavaCaptureCard");
        Group root = new Group();
        StackPane pane = new StackPane();
        pane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        root.getChildren().add(pane);



        ComboBox cb = new ComboBox();
        cb.setTranslateX(SCENE_WIDTH/2-90-cb.getBoundsInLocal().getWidth()/2);
        cb.setTranslateY(SCENE_HEIGHT/2-100-cb.getBoundsInLocal().getHeight()/2);
        cb.setPrefWidth(150);

        root.getChildren().add(cb);


        // Add button to scene
        Button button = new Button();
        button.setText("Connect");
        button.setTranslateX(SCENE_WIDTH/2-50-button.getBoundsInLocal().getWidth()/2);
        button.setTranslateY(SCENE_HEIGHT/2-50-button.getBoundsInLocal().getHeight()/2);
        root.getChildren().add(button);

        System.out.println(SerialPort.getCommPorts().length);

        Text connectionText = new Text("AAAAAAAAAAAAAAa");
        // centre it below the connect button
        connectionText.setTranslateX(SCENE_WIDTH/2-50-connectionText.getBoundsInLocal().getWidth()/2);
        connectionText.setTranslateY(SCENE_HEIGHT/2+50-connectionText.getBoundsInLocal().getHeight()/2);
        connectionText.setStyle("-fx-text-fill: red");

        if (this.detectConnection) {
            connectionText.setText("Detected CH9329 Cable!");
        } else {
            connectionText.setText("Please connect CH9329 Cable!");
        }
        root.getChildren().add(connectionText);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}