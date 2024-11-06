/**
 * Class is inspired from https://github.com/SmallCodeNote/CH9329-109KeyClass & https://github.com/sipper69/Control3/
 */

package com.javacapturecard.app;

import com.fazecast.jSerialComm.SerialPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ch9329 {
    public String portName;
    public int baudRate;
    public int xRes;
    public int yRes;
    public SerialPort serialPort;

    public byte CHIP_VERSION;
    public byte CHIP_STATUS;
    public boolean NUM_LOCK;
    public boolean CAPS_LOCK;
    public boolean SCROLL_LOCK;


    Map<Byte, Byte> keyMap;
    Map<mediaKeys, byte[]> mediaKeyMap;
    

    public ch9329() {
        this("COM5",1920,1080,9600);
    }

    public ch9329(String portName, int xRes, int yRes, int baudRate) {
        this.portName = portName;
        this.xRes = xRes;
        this.yRes = yRes;
        this.baudRate = baudRate;

        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(baudRate);

        if (serialPort.openPort()) {
            System.out.println("Port Open");
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        } else {
            System.out.println("Port Not Open");
        }

        createKeyMap();
        createMediaKeyMap();

    }

    public enum mediaKeys {
        EJECT,
        CDSTOP,
        PREVTRACk,
        NEXTTRACK,
        PLAYPAUSE,
        MUTE,
        VOLUMEDOWN,
        VOLUMEUP
    }

    public void createMediaKeyMap() {
        mediaKeyMap = new HashMap<>();
        mediaKeyMap.put(mediaKeys.EJECT, new byte[] {(byte) 0x02, (byte) 0x80, 0x00, 0x00});
        mediaKeyMap.put(mediaKeys.CDSTOP, new byte[] {(byte) 0x02, (byte) 0x40, 0x00, 0x00});
        mediaKeyMap.put(mediaKeys.PREVTRACk, new byte[] {(byte) 0x02, (byte) 0x20, 0x00, 0x00});
        mediaKeyMap.put(mediaKeys.NEXTTRACK, new byte[] { (byte) 0x02, (byte) 0x10, 0x00, 0x00 });
        mediaKeyMap.put(mediaKeys.PLAYPAUSE, new byte[] { (byte) 0x02, (byte) 0x08, 0x00, 0x00 });
        mediaKeyMap.put(mediaKeys.MUTE, new byte[] {(byte) 0x02, (byte) 0x04, 0x00, 0x00 });
        mediaKeyMap.put(mediaKeys.VOLUMEDOWN, new byte[] {(byte) 0x02, (byte) 0x02, 0x00, 0x00 });
        mediaKeyMap.put(mediaKeys.VOLUMEUP, new byte[] { 0x02, 0x01, 0x00, 0x00 });
    }


    /**
     * Ascii value corresponding to c9329 keycode values
     */
    public void createKeyMap() {
        keyMap = new HashMap<>();

        keyMap.put((byte) 8, (byte) 0x2A);  // Back
        keyMap.put((byte) 9, (byte) 0x2B);  // Tab
        keyMap.put((byte) 13, (byte) 0x28);  // Enter
        keyMap.put((byte) 19, (byte) 0x48);  // Pause
        keyMap.put((byte) 20, (byte) 0x39);  // Caps Lock
        keyMap.put((byte) 27, (byte) 0x29);  // Escape
        keyMap.put((byte) 32, (byte) 0x2C);  // Space
        keyMap.put((byte) 33, (byte) 0x4B);  // PageUp
        keyMap.put((byte) 34, (byte) 0x4E);  // Next
        keyMap.put((byte) 35, (byte) 0x4D);  // End
        keyMap.put((byte) 36, (byte) 0x4A);  // Home
        keyMap.put((byte) 37, (byte) 0x50);  // Left
        keyMap.put((byte) 38, (byte) 0x52);  // Up
        keyMap.put((byte) 39, (byte) 0x4F);  // Right
        keyMap.put((byte) 40, (byte) 0x51);  // Down
        keyMap.put((byte) 44, (byte) 0x46);  // PrintScreen
        keyMap.put((byte) 45, (byte) 0x49);  // Insert
        keyMap.put((byte) 46, (byte) 0x4C);  // Delete
        keyMap.put((byte) 48, (byte) 0x27);  // 0
        keyMap.put((byte) 49, (byte) 0x1E);  // 1
        keyMap.put((byte) 50, (byte) 0x1F);  // 2
        keyMap.put((byte) 51, (byte) 0x20);  // 3
        keyMap.put((byte) 52, (byte) 0x21);  // 4
        keyMap.put((byte) 53, (byte) 0x22);  // 5
        keyMap.put((byte) 54, (byte) 0x23);  // 6
        keyMap.put((byte) 55, (byte) 0x24);  // 7
        keyMap.put((byte) 56, (byte) 0x25);  // 8
        keyMap.put((byte) 57, (byte) 0x26);  // 9
        keyMap.put((byte) 65, (byte) 0x04);  // A
        keyMap.put((byte) 66, (byte) 0x05);  // B
        keyMap.put((byte) 67, (byte) 0x06);  // C
        keyMap.put((byte) 68, (byte) 0x07);  // D
        keyMap.put((byte) 69, (byte) 0x08);  // E
        keyMap.put((byte) 70, (byte) 0x09);  // F
        keyMap.put((byte) 71, (byte) 0x0A);  // G
        keyMap.put((byte) 72, (byte) 0x0B);  // H
        keyMap.put((byte) 73, (byte) 0x0C);  // I
        keyMap.put((byte) 74, (byte) 0x0D);  // J
        keyMap.put((byte) 75, (byte) 0x0E);  // K
        keyMap.put((byte) 76, (byte) 0x0F);  // L
        keyMap.put((byte) 77, (byte) 0x10);  // M
        keyMap.put((byte) 78, (byte) 0x11);  // N
        keyMap.put((byte) 79, (byte) 0x12);  // O
        keyMap.put((byte) 80, (byte) 0x13);  // P
        keyMap.put((byte) 81, (byte) 0x14);  // Q
        keyMap.put((byte) 82, (byte) 0x15);  // R
        keyMap.put((byte) 83, (byte) 0x16);  // S
        keyMap.put((byte) 84, (byte) 0x17);  // T
        keyMap.put((byte) 85, (byte) 0x18);  // U
        keyMap.put((byte) 86, (byte) 0x19);  // V
        keyMap.put((byte) 87, (byte) 0x1A);  // W
        keyMap.put((byte) 88, (byte) 0x1B);  // X
        keyMap.put((byte) 89, (byte) 0x1C);  // Y
        keyMap.put((byte) 90, (byte) 0x1D);  // Z
        keyMap.put((byte) 91, (byte) 0xE3);  // LWin
        keyMap.put((byte) 93, (byte) 0x65);  // Applications
        keyMap.put((byte) 96, (byte) 0x62);  // NumPad 0
        keyMap.put((byte) 97, (byte) 0x59);  // NumPad 1
        keyMap.put((byte) 98, (byte) 0x5A);  // NumPad 2
        keyMap.put((byte) 99, (byte) 0x5B);  // NumPad 3
        keyMap.put((byte) 100, (byte) 0x5C); // NumPad 4
        keyMap.put((byte) 101, (byte) 0x5D); // NumPad 5
        keyMap.put((byte) 102, (byte) 0x5E); // NumPad 6
        keyMap.put((byte) 103, (byte) 0x5F); // NumPad 7
        keyMap.put((byte) 104, (byte) 0x60); // NumPad 8
        keyMap.put((byte) 105, (byte) 0x61); // NumPad 9
        keyMap.put((byte) 106, (byte) 0x55); // NumPad Multiply
        keyMap.put((byte) 107, (byte) 0x57); // NumPad Add
        keyMap.put((byte) 109, (byte) 0x56); // NumPad Subtract
        keyMap.put((byte) 110, (byte) 0x63); // NumPad Decimal
        keyMap.put((byte) 111, (byte) 0x54); // NumPad Divide
        keyMap.put((byte) 112, (byte) 0x3A); // F1
        keyMap.put((byte) 113, (byte) 0x3B); // F2
        keyMap.put((byte) 114, (byte) 0x3C); // F3
        keyMap.put((byte) 115, (byte) 0x3D); // F4
        keyMap.put((byte) 116, (byte) 0x3E); // F5
        keyMap.put((byte) 117, (byte) 0x3F); // F6
        keyMap.put((byte) 118, (byte) 0x40); // F7
        keyMap.put((byte) 119, (byte) 0x41); // F8
        keyMap.put((byte) 120, (byte) 0x42); // F9
        keyMap.put((byte) 121, (byte) 0x43); // F10
        keyMap.put((byte) 122, (byte) 0x44); // F11
        keyMap.put((byte) 123, (byte) 0x45); // F12
        keyMap.put((byte) 144, (byte) 0x53); // NumLock
        keyMap.put((byte) 160, (byte) 0xE1); // LShiftKey
        keyMap.put((byte) 161, (byte) 0xE5); // RShiftKey
        keyMap.put((byte) 162, (byte) 0xE0); // LControlKey
        keyMap.put((byte) 163, (byte) 0xE4); // RControlKey
        keyMap.put((byte) 164, (byte) 0xE2); // LAlt
        keyMap.put((byte) 165, (byte) 0xE6); // RAlt
        keyMap.put((byte) 186, (byte) 0x33); // ;
        keyMap.put((byte) 187, (byte) 0x2E); // =
        keyMap.put((byte) 188, (byte) 0x36); // ,
        keyMap.put((byte) 189, (byte) 0x2D); // -
        keyMap.put((byte) 190, (byte) 0x37); // .
        keyMap.put((byte) 191, (byte) 0x38); // /
        keyMap.put((byte) 192, (byte) 0x35); // `
        keyMap.put((byte) 219, (byte) 0x2F); // [
        keyMap.put((byte) 220, (byte) 0x31); // \
        keyMap.put((byte) 221, (byte) 0x30); // ]
        keyMap.put((byte) 222, (byte) 0x34); // '
    }

    public enum SpecialKeyCode {
        ENTER((byte) 0x28),
        ESCAPE((byte) 0x29),
        BACKSPACE((byte) 0x2A),
        TAB((byte) 0x2B),
        SPACEBAR((byte) 0x2C),
        CAPS_LOCK((byte) 0x39),
        F1((byte) 0x3A),
        F2((byte) 0x3B),
        F3((byte) 0x3C),
        F4((byte) 0x3D),
        F5((byte) 0x3E),
        F6((byte) 0x3F),
        F7((byte) 0x40),
        F8((byte) 0x41),
        F9((byte) 0x42),
        F10((byte) 0x43),
        F11((byte) 0x44),
        F12((byte) 0x45),
        PRINTSCREEN((byte) 0x46),
        SCROLL_LOCK((byte) 0x47),
        PAUSE((byte) 0x48),
        INSERT((byte) 0x49),
        HOME((byte) 0x4A),
        PAGEUP((byte) 0x4B),
        DELETE((byte) 0x4C),
        END((byte) 0x4D),
        PAGEDOWN((byte) 0x4E),
        RIGHTARROW((byte) 0x4F),
        LEFTARROW((byte) 0x50),
        DOWNARROW((byte) 0x51),
        UPARROW((byte) 0x52),
        APPLICATION((byte) 0x65),
        LEFT_CTRL((byte) 0xE0),
        LEFT_SHIFT((byte) 0xE1),
        LEFT_ALT((byte) 0xE2),
        LEFT_WINDOWS((byte) 0xE3),
        RIGHT_CTRL((byte) 0xE4),
        RIGHT_SHIFT((byte) 0xE5),
        RIGHT_ALT((byte) 0xE6),
        RIGHT_WINDOWS((byte) 0xE7),
        CTRL((byte) 0xE4),
        SHIFT((byte) 0xE5),
        ALT((byte) 0xE6),
        WINDOWS((byte) 0xE7);

        private final byte code;

        SpecialKeyCode(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }

    public enum KeyGroup {
        CKey((byte) 0x02),
        MKey((byte) 0x03);

        private final byte code;

        KeyGroup(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }

    public enum MouseCode {
        LEFT((byte) 0x01),
        RIGHT((byte) 0x02),
        MIDDLE((byte) 0x04);

        private final byte code;

        MouseCode(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }

    public enum CommandCode {
        GET_INFO((byte) 0x01),
        SEND_KB_GENERAL_DATA((byte) 0x02),
        SEND_KB_MEDIA_DATA((byte) 0x03),
        SEND_MS_ABS_DATA((byte) 0x04),
        SEND_MS_REL_DATA((byte) 0x05),
        READ_MY_HID_DATA((byte) 0x07),
        GET_PARA_CFG((byte) 0x08),
        GET_USB_STRING((byte) 0x0A);

        private final byte code;
        CommandCode(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;

        }
    }

    public void getInfo() {

    }

    public byte[] createPacket(List<Integer> list) {
        List<Byte> bytePacketList = new ArrayList<>();

        for (int l : list) {
            bytePacketList.add((byte)l);
        }

        return bytePacketList.stream()
                .collect(() -> new byte[bytePacketList.size()],
                        (arr, b) -> arr[bytePacketList.indexOf(b)] = b,
                        (arr1, arr2) -> {});
    }

    public String sendPacket(byte[] data) {
        String s = "Packet Sent!";
        Thread t = new Thread(() -> {
            serialPort.writeBytes(data, data.length);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t.start();

        return s;
    }
}
