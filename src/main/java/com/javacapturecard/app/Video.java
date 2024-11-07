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
    static String s = "";
    public static List<Integer> detectVideoSources() {
        List<Integer> videoSources = new ArrayList<>();


        for (int i = 0; i < 1; i++) {
            try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(i)) {
                grabber.start();
                s = grabber.getFormat();
                videoSources.add(i);
                grabber.stop();
            } catch (FrameGrabber.Exception e) {

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
        System.out.println(s);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
