package com.catchzeng.swiftbluetoothandroid.bluetooth.model;

import android.bluetooth.BluetoothDevice;

public class Peripheral {
    public static Boolean isValid(BluetoothDevice device) {
        return device.getName().startsWith("Makeblock_LE");
    }
}
