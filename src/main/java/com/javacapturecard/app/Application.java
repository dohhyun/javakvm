package javacapturecard.app;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

    private static final int DISPLAY_WIDTH = 1920;
    private static final int DISPLAY_HEIGHT = 1080;




    @Override
    public void start(Stage stage) throws IOException {
        Video video = new Video();

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            video.setOperatingSystem(OperatingSystem.WINDOWS);

        } else if (os.contains("nux")) {
            video.setOperatingSystem(OperatingSystem.LINUX);


        } else if (os.contains("mac")) {
            video.setOperatingSystem(OperatingSystem.MAC);

        } else {
            video.setOperatingSystem(OperatingSystem.UNKNOWN);
            System.err.println("Unsupported operating system: " + os);
            return;
        }

        CH9329 connection = new CH9329(video.getOperatingSystem());

        stage.setTitle("JavaCaptureCard");
        Group root = new Group();
        StackPane pane = new StackPane();
        pane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        root.getChildren().add(pane);


        ObservableList<VideoDevice> videoSources = FXCollections.observableArrayList();

        ComboBox<VideoDevice> cb = new ComboBox<>(videoSources);
        cb.setTranslateX(SCENE_WIDTH/2-200-cb.getBoundsInLocal().getWidth()/2);
        cb.setTranslateY(SCENE_HEIGHT/2-100-cb.getBoundsInLocal().getHeight()/2);
        cb.setPrefWidth(400);
        video.detectVideoSources();
        videoSources.addAll(video.getVideoDevices());

        videoSources.forEach(System.out::println);


        videoSources.addListener((ListChangeListener<? super VideoDevice>) change -> {
            if (change.wasAdded()) {
                videoSources.clear();
                videoSources.addAll(change.getAddedSubList());
            }
        });


        root.getChildren().add(cb);


        // Add button to scene
        Button button = new Button();
        button.setText("Connect");
        button.setTranslateX(SCENE_WIDTH/2-50-button.getBoundsInLocal().getWidth()/2);
        button.setTranslateY(SCENE_HEIGHT/2-50-button.getBoundsInLocal().getHeight()/2);
        root.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            if (!video.isRunning()) {
                if (cb.getValue() != null) {
                    System.out.println("Selected: " + cb.getValue());
                    openDisplayWindow(video, cb.getValue());
                } else {
                    System.out.println("You have selected nothing!");
                }
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

    private void openDisplayWindow(Video video, VideoDevice videoDevice) {

        new Thread(() -> video.displayVideo(videoDevice)).start();
    }

    public static void main(String[] args) {
        launch();
    }

}