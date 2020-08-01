package com.software.account.service.interfaces;

import com.software.account.query.RegisteredDevice;

import java.util.List;

public interface IDeviceService {
    List<RegisteredDevice> findAll();
    void save(RegisteredDevice device);
    void confirmDevice(RegisteredDevice device);
    void confirmDevice(String id);
    void rejectDevice(RegisteredDevice device);
    void rejectDevice(String id);
    RegisteredDevice findById(String id);
}
