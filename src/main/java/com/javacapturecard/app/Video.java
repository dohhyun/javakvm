package com.javacapturecard.app;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import javafx.embed.swing.SwingFXUtils;


public class Video {
    private boolean isRunning = false;
    public static List<Integer> detectVideoSources() {
        List<Integer> videoSources = new ArrayList<>();


        for (int i = 4; i < 5; i++) {
            try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(i)) {
                grabber.start();

                videoSources.add(i);

            } catch (FrameGrabber.Exception e) {
                System.out.println("Device index " + i + " is not available.");

            }
        }

        return videoSources;
    }

    public void displayVideo(ImageView imageView) {

        try (OpenCVFrameGrabber grabber =  new OpenCVFrameGrabber(0)) {
            grabber.start();

            Java2DFrameConverter converter = new Java2DFrameConverter();

            while (isRunning) {
                Frame frame = grabber.grab();
                if (frame != null) {
                    BufferedImage bufferedImage = converter.convert(frame);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                    Platform.runLater(() -> imageView.setImage(image));
                }
            }

            grabber.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Integer> l = detectVideoSources();
        System.out.println(l);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
