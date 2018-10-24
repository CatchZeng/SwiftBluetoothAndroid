package com.catchzeng.swiftbluetoothandroid;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.catchzeng.swiftbluetoothandroid.bluetooth.SwiftBluetooth;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_LOCATION = 1;

    private SwiftBluetooth swiftBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSwiftBluetooth();
        checkBluetooth();
    }

    private void initSwiftBluetooth() {
        swiftBluetooth = new SwiftBluetooth(getApplication());
        swiftBluetooth.enableLog(true);
    }

    private void checkBluetooth() {
        if (!swiftBluetooth.isSupportBle()) {
            Toast.makeText(this,"手机不支持BLE", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!swiftBluetooth.isBleEnable()) {
            swiftBluetooth.enableBluetoth();
        }

        checkPermissions();
    }

    private void checkPermissions() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            PermissionRequest request = new PermissionRequest.Builder(this, RC_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).build();
            EasyPermissions.requestPermissions(request);
            return;
        }
        swiftBluetooth.scan();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        swiftBluetooth.scan();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this,"未允许位置权限，蓝牙无法工作", Toast.LENGTH_SHORT).show();
    }
}