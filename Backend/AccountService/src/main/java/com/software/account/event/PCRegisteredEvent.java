package com.software.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PCRegisteredEvent {
    private final String deviceId;
    private final String deviceName;
}
