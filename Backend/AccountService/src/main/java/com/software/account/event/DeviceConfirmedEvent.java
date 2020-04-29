package com.software.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceConfirmedEvent {
    private final String id;
}
