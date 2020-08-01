package com.software.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmartwatchRegisteredEvent {
    private final String deviceId;
    private final String deviceName;
}
