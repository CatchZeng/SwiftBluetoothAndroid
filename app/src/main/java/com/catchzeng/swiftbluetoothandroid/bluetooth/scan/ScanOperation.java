package com.catchzeng.swiftbluetoothandroid.bluetooth.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;

import com.catchzeng.swiftbluetoothandroid.bluetooth.model.Peripheral;
import com.catchzeng.swiftbluetoothandroid.bluetooth.util.LogUtil;

public class ScanOperation {
    private BluetoothAdapter bluetoothAdapter;

    public ScanOperation(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public void start() {
        BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanner.startScan(scanCallback);
    }

    public void stopScan() {
        BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanner.stopScan(scanCallback);
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (!Peripheral.isValid(device)) {
                return;
            }

            int rssi = result.getRssi();
            LogUtil.e("onScanResult: "+ device.getName() + " # " + rssi);
        }

        @Override
        public void onScanFailed(int errorCode) {
            LogUtil.e("onScanFailed: " + errorCode);
        }
    };
}