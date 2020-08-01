package com.software.account.command;

import com.software.account.aggregate.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RegisterDeviceCommand {
    @TargetAggregateIdentifier
    private final String accountId;
    private final String deviceId;
    private final String deviceName;
    private final DeviceType deviceType;
}
