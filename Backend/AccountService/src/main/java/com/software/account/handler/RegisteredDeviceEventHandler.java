package com.software.account.handler;

import com.software.account.event.DeviceConfirmedEvent;
import com.software.account.event.DeviceRegisteredEvent;
import com.software.account.event.DeviceRejectedEvent;
import com.software.account.handler.interfaces.IRegisteredDeviceEventHandler;
import com.software.account.query.FindAllRegisteredDevicesQuery;
import com.software.account.query.FindDeviceByIdQuery;
import com.software.account.query.RegisteredDevice;
import com.software.account.service.interfaces.IDeviceService;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisteredDeviceEventHandler implements IRegisteredDeviceEventHandler {
    private final IDeviceService deviceService;

    public RegisteredDeviceEventHandler(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @EventHandler
    public void on(DeviceRegisteredEvent event) {
        deviceService.save(new RegisteredDevice(event.getDeviceId(), event.getDeviceName(), event.getDeviceType()));
    }

    @EventHandler
    public void on(DeviceConfirmedEvent event) {
        deviceService.confirmDevice(event.getId());
    }

    @EventHandler
    public void on(DeviceRejectedEvent event) {
        deviceService.rejectDevice(event.getId());
    }

    @QueryHandler
    public List<RegisteredDevice> handle(FindAllRegisteredDevicesQuery query) {
        return deviceService.findAll();
    }

    @QueryHandler
    public RegisteredDevice handle(FindDeviceByIdQuery query) {
        return deviceService.findById(query.getId());
    }
}
