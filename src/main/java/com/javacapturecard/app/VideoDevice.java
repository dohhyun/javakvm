package com.javacapturecard.app;

public class VideoDevice {
    private String name;
    private String path;

    public VideoDevice(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name;
    }
}
