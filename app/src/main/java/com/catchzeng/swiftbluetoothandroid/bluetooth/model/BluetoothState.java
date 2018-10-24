package com.catchzeng.swiftbluetoothandroid.bluetooth.model;

public enum BluetoothState {
    STATE_IDLE(-1),
    STATE_SCANNING(0X01),
    STATE_CONNECTING(0X02),
    STATE_CONNECTED(0X03);

    private int code;

    BluetoothState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
