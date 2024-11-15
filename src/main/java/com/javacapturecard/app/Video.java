package com.javacapturecard.app;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bytedeco.javacv.*;
import javafx.embed.swing.SwingFXUtils;


public class Video {
    private boolean isRunning = false;
    private static final int VIDEO_WIDTH = 1920;
    private static final int VIDEO_HEIGHT = 1080;
    public HashMap<Integer, String> videoSourceMap;

    public static HashMap<Integer, String> detectVideoSources() {
        HashMap<Integer, String> videoSourceMap = new HashMap<>();

    }

    public void displayVideo() {

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("/dev/video4")) {
            grabber.setFormat("v4l2");
            grabber.setOption("input_format", "yuv420p");
            grabber.setOption("rtbufsize", "64M");
            grabber.setFrameRate(30);
            grabber.setImageWidth(VIDEO_WIDTH);
            grabber.setImageHeight(VIDEO_HEIGHT);


            grabber.start();

            CanvasFrame canvas = new CanvasFrame("Display", CanvasFrame.getDefaultGamma() / grabber.getGamma());
            // Set a custom close operation
            canvas.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
            canvas.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    isRunning = false;
                    System.out.println("CanvasFrame closed, stopping video capture.");
                }
            });

            while (isRunning && canvas.isVisible()) {
                Frame frame = grabber.grab();
                if (frame != null) {
                    canvas.showImage(frame);
                }
            }

            grabber.stop();
            canvas.dispose();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
