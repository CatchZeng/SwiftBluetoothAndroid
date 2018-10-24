package com.catchzeng.swiftbluetoothandroid.bluetooth;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.catchzeng.swiftbluetoothandroid.bluetooth.model.Peripheral;
import com.catchzeng.swiftbluetoothandroid.bluetooth.model.PeripheralContainer;
import com.catchzeng.swiftbluetoothandroid.bluetooth.model.BluetoothState;
import com.catchzeng.swiftbluetoothandroid.bluetooth.util.LogUtil;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;
import static android.bluetooth.BluetoothAdapter.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;

public class SwiftBluetooth {
    private BluetoothAdapter bluetoothAdapter;
    private Application application;
    private PeripheralContainer container = new PeripheralContainer();
    private Boolean enableCloseToConnect = true;
    private BluetoothState state = BluetoothState.STATE_IDLE;

    public SwiftBluetooth(Application application) {
        this.application = application;
        initAdapter();
    }

    public void enableLog(Boolean enable) {
        LogUtil.enableLog = enable;
    }

    public void setEnableCloseToConnect(Boolean enableCloseToConnect) {
        this.enableCloseToConnect = enableCloseToConnect;
    }

    public void startScan() {
        BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanner.startScan(scanCallback);
        state = BluetoothState.STATE_SCANNING;
    }

    public void stopScan() {
        BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanner.stopScan(scanCallback);
    }

    public void connect(Peripheral peripheral) {
        if (state == BluetoothState.STATE_CONNECTING) {
            return;
        }
        stopScan();
        state = BluetoothState.STATE_CONNECTING;
        peripheral.getDevice().connectGatt(application, false, gattCallback);
    }

    public boolean isSupportBle() {
        return application.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public boolean isBluetoothEnable() {
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

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (!Peripheral.isValid(device)) {
                return;
            }
            int rssi = result.getRssi();
            Peripheral peripheral = new Peripheral(device,rssi);
            container.append(peripheral);

            closeToConnect(peripheral);
        }

        @Override
        public void onScanFailed(int errorCode) {
            LogUtil.e("onScanFailed: " + errorCode);
        }
    };

    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            LogUtil.i("newState: " + newState);

            if (newState == STATE_CONNECTED) {
                LogUtil.i("CONNECTED");

            } else if (newState == STATE_DISCONNECTED) {
                LogUtil.i("DISCONNECTED");
            }
        }
    };

    private void closeToConnect(@NonNull Peripheral peripheral) {
        if (!enableCloseToConnect || state == BluetoothState.STATE_CONNECTING || state == BluetoothState.STATE_CONNECTED) {
            return;
        }

        if (peripheral.getRssi() > -40) {
            connect(peripheral);
        }
    }
}