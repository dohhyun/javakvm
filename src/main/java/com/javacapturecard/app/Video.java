package com.javacapturecard.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bytedeco.javacv.*;


public class Video {
    private boolean isRunning = false;
    private static final int VIDEO_WIDTH = 1280;
    private static final int VIDEO_HEIGHT = 720;
    private String operatingSystem;
    public ArrayList<VideoDevice> videoDevices = new ArrayList<>();

    public void displayVideo(VideoDevice device) {

        if (isRunning) {
            return;
        }

        setRunning(true);

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(device.getPath())) {
            grabber.setFormat("v4l2");
            grabber.setOption("input_format", "yuv420p");
            grabber.setOption("rtbufsize", "1M");
            grabber.setFrameRate(0);
            grabber.setImageWidth(VIDEO_WIDTH);
            grabber.setImageHeight(VIDEO_HEIGHT);
            grabber.setOption("fflags", "nobuffer");
            grabber.setOption("flags", "low_delay");
            grabber.setOption("hwaccel", "auto");


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

    public void detectVideoSources() {
        String[] command = new String[] {};

        if (getOperatingSystem().equals("Windows")) {
        } else if (getOperatingSystem().equals("Linux")) {
            command = new String[] {"v4l2-ctl", "--list-devices"};
        } else if (getOperatingSystem().equals("Mac")) {

        }

        try {
            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String curName = null;
            if (getOperatingSystem().equals("Linux")) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        if (!line.startsWith("/dev/video")) {
                            curName = line;
                        } else if (curName != null) {
                            videoDevices.add(new VideoDevice(curName, line));
                            curName = null;
                        }
                    }
                }
            }


            process.waitFor();
        } catch (Exception e) {
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

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public ArrayList<VideoDevice> getVideoDevices() {
        return videoDevices;
    }
}
