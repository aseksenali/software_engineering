package com.software.account.command;

import com.software.account.event.DeviceRegisteredEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RegisterSmartphoneCommand {
    @TargetAggregateIdentifier
    private final String deviceId;
    private final String deviceName;
}
