package com.catchzeng.swiftbluetoothandroid.bluetooth.model;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;

public class Peripheral {
    private BluetoothDevice device;
    private String address;
    private String name;
    private int rssi;

    public Peripheral(BluetoothDevice device, int rssi) {
        this.device = device;
        this.address = device.getAddress();
        this.name = device.getName();
        this.rssi = rssi;
    }

    @NonNull
    public static Boolean isValid(@NonNull BluetoothDevice device) {
        if (device.getName() == null) {
            return false;
        }

        return device.getName().startsWith("Makeblock_LE");
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "name:" + name + "rssi:" + rssi + "address:"+ address;
    }
}
