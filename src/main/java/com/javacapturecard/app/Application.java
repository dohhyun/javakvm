package com.javacapturecard.app;

import com.fazecast.jSerialComm.SerialPort;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {


    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 250;

    private static final int DISPLAY_WIDTH = 1280;
    private static final int DISPLAY_HEIGHT = 720;


    @Override
    public void start(Stage stage) throws IOException {
        CH9329 connection = new CH9329();
        Video video = new Video();

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
        button.setOnAction(actionEvent -> {
            if (!video.isRunning()) {
                openDisplayWindow(video);
            }

        });

        Text connectionText = new Text("");
        // centre it below the connect button
        connectionText.setTranslateX(SCENE_WIDTH/2-100-connectionText.getBoundsInLocal().getWidth()/2);
        connectionText.setTranslateY(SCENE_HEIGHT/2+25-connectionText.getBoundsInLocal().getHeight()/2);
        connectionText.setStyle("-fx-text-fill: red");

        if (connection.detectConnection) {
            connectionText.setText("Detected CH9329 Cable!");
        } else {
            connectionText.setText("Please connect CH9329 Cable!");
        }
        root.getChildren().add(connectionText);


        stage.setScene(scene);
        stage.show();
    }

    private void openDisplayWindow(Video video) {
        video.setRunning(true);
        Stage videoWindow = new Stage();
        videoWindow.setTitle("Video Display");

        StackPane g = new StackPane();

        ImageView iv = new ImageView();

        iv.setFitWidth(DISPLAY_WIDTH);
        iv.setFitHeight(DISPLAY_HEIGHT);
        new Thread(() -> {
            video.displayVideo(iv);

        }).start();
        videoWindow.setOnCloseRequest(event -> {
            video.setRunning(false);
            System.out.println("Closed Video!");
        });

        g.getChildren().add(iv);
        videoWindow.setScene(new Scene(g, DISPLAY_WIDTH, DISPLAY_HEIGHT));
        videoWindow.show();






    }

    public static void main(String[] args) {
        launch();
    }

}