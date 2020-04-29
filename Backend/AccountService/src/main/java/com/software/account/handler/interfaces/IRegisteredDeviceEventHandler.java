package com.software.account.handler.interfaces;

import com.software.account.event.DeviceConfirmedEvent;
import com.software.account.event.DeviceRegisteredEvent;
import com.software.account.event.DeviceRejectedEvent;
import com.software.account.query.FindAllRegisteredDevicesQuery;
import com.software.account.query.FindDeviceByIdQuery;
import com.software.account.query.RegisteredDevice;

import java.util.List;

public interface IRegisteredDeviceEventHandler {
    void on(DeviceRegisteredEvent event);
    void on(DeviceConfirmedEvent event);
    void on(DeviceRejectedEvent event);
    List<RegisteredDevice> handle(FindAllRegisteredDevicesQuery query);
    RegisteredDevice handle(FindDeviceByIdQuery query);
}
