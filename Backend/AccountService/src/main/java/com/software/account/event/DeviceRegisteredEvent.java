package com.software.account.event;

import com.software.account.aggregate.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceRegisteredEvent {
    private final String accountId;
    private final String deviceId;
    private final String deviceName;
    private final DeviceType deviceType;
}
