package com.software.account.aggregate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.software.account.command.ConfirmDeviceCommand;
import com.software.account.command.RegisterDeviceCommand;
import com.software.account.command.RejectDeviceCommand;
import com.software.account.event.DeviceConfirmedEvent;
import com.software.account.event.DeviceRegisteredEvent;
import com.software.account.event.DeviceRejectedEvent;
import com.software.account.query.RegisteredDevice;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=PC.class, name = "PC"),
        @JsonSubTypes.Type(value=SmartPhone.class, name = "SmartPhone"),
        @JsonSubTypes.Type(value=SmartWatch.class, name="SmartWatch")
})
public abstract class Device {
    @EntityId
    private String deviceId;
    private String deviceName;
    private DeviceStatus status;

    @Setter(onMethod = @__(@Autowired))
    private CommandGateway commandGateway;

    public Device(String deviceId, String deviceName, DeviceStatus status) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.status = status;
    }

    public abstract boolean confirmDevice();
}
