package com.software.account.service;

import com.software.account.exception.DeviceNotFoundException;
import com.software.account.query.RegisteredDevice;
import com.software.account.repository.DeviceRepository;
import com.software.account.service.interfaces.IDeviceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService implements IDeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<RegisteredDevice> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public void save(RegisteredDevice device) {
        deviceRepository.save(device);
    }

    @Override
    public RegisteredDevice findById(String id) {
        return deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(
                "Device with id: " + id + " is not found."
        ));
    }


    @Override
    public void confirmDevice(RegisteredDevice device) {
        device.setDeviceConfirmed();
        deviceRepository.save(device);
    }

    @Override
    public void confirmDevice(String id) {
        RegisteredDevice device = findById(id);
        confirmDevice(device);
    }

    @Override
    public void rejectDevice(RegisteredDevice device) {
        device.setDeviceRejected();
        deviceRepository.save(device);
    }

    @Override
    public void rejectDevice(String id) {
        RegisteredDevice device = findById(id);
        rejectDevice(device);
    }
}
