package com.catchzeng.swiftbluetoothandroid.bluetooth.model;

import com.catchzeng.swiftbluetoothandroid.bluetooth.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PeripheralContainer {
    private List<Peripheral> peripherals = new ArrayList<>();

    public void append(Peripheral peripheral) {
        if (peripheral == null) return;

        AtomicBoolean hasFound = new AtomicBoolean(false);
        for (Peripheral p : peripherals) {
            if (p.getAddress().equals(peripheral.getAddress())) {
                p.setRssi(peripheral.getRssi());
                hasFound.set(true);
            }
        }

        if (hasFound.get()) {
            LogUtil.i("update: "+ peripheral.toString());
        } else {
            peripherals.add(peripheral);
            LogUtil.i("append: "+ peripheral.toString());
        }
    }

    public List<Peripheral> getPeripherals() {
        return peripherals;
    }
}
