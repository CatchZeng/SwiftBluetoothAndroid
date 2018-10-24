package com.catchzeng.swiftbluetoothandroid.bluetooth;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.catchzeng.swiftbluetoothandroid.bluetooth.util.LogUtil;
import com.catchzeng.swiftbluetoothandroid.bluetooth.scan.ScanOperation;

public class SwiftBluetooth {
    private BluetoothAdapter bluetoothAdapter;
    private Application application;
    private ScanOperation scanOperation;

    public SwiftBluetooth(Application application) {
        this.application = application;
        initAdapter();
    }

    public void enableLog(Boolean enable) {
        LogUtil.enableLog = enable;
    }

    public void scan() {
        scanOperation = new ScanOperation(bluetoothAdapter);
        scanOperation.start();
    }

    public boolean isSupportBle() {
        return application.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public boolean isBleEnable() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public void enableBluetoth() {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    public void disableBluetooth() {
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled())
                bluetoothAdapter.disable();
        }
    }

    private void initAdapter() {
        if (!isSupportBle()) {
            return;
        }

        final BluetoothManager manager = (BluetoothManager) application.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = manager.getAdapter();
    }
}