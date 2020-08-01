package com.software.account.aggregate;

import com.software.account.command.*;
import com.software.account.event.AccountActivatedEvent;
import com.software.account.event.AccountCreatedEvent;
import com.software.account.event.AccountDeactivatedEvent;
import com.software.account.event.DeviceRegisteredEvent;
import com.software.account.exception.DeviceNotFoundException;
import com.software.account.query.RegisteredDevice;
import lombok.Data;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
@Aggregate
public class Account {
    @AggregateIdentifier
    private String id;
    private String username;
    @AggregateMember
    private List<RegisteredDevice> devices;
    private boolean isActive;

    @Setter(onMethod = @__(@Autowired))
    private CommandGateway commandGateway;

    @CommandHandler
    public Account(CreateAccountCommand command) {
        AggregateLifecycle.apply(new AccountCreatedEvent(command.getId(), command.getUsername()));
    }

    @CommandHandler
    public void handle(RegisterDeviceCommand command) {
        AggregateLifecycle.apply(new DeviceRegisteredEvent(command.getAccountId(), command.getDeviceId(), command.getDeviceName(), command.getDeviceType()));
    }

    @CommandHandler
    public void handle(DeactivateAccountCommand command) {
        AggregateLifecycle.apply(new AccountDeactivatedEvent(command.getId()));
    }

    @CommandHandler
    public void handle(ActivateAccountCommand command) {
        AggregateLifecycle.apply(new AccountActivatedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.id = event.getId();
        this.username = event.getUsername();
        this.devices = new ArrayList<>();
        this.isActive = true;
    }

    @EventSourcingHandler
    public void on(DeviceRegisteredEvent event) {
        switch(event.getDeviceType()) {
            case PC:
                commandGateway.send(new RegisterPCCommand(event.getDeviceId(), event.getDeviceName()));
                this.devices.add(new RegisteredDevice(event.getDeviceId(), event.getDeviceName(), DeviceType.PC));
                break;
            case SMARTPHONE:
                commandGateway.send(new RegisterSmartphoneCommand(event.getDeviceId(), event.getDeviceName()));
                this.devices.add(new RegisteredDevice(event.getDeviceId(), event.getDeviceName(), DeviceType.SMARTPHONE));
                break;
            case SMARTWATCH:
                commandGateway.send(new RegisterSmartwatchCommand(event.getDeviceId(), event.getDeviceName()));
                this.devices.add(new RegisteredDevice(event.getDeviceId(), event.getDeviceName(), DeviceType.SMARTWATCH));
                break;
            default:
                throw new DeviceNotFoundException("Given device type not found");
        }
        commandGateway.send(new ConfirmDeviceCommand(event.getDeviceId()));
    }

    @EventSourcingHandler
    public void on(AccountDeactivatedEvent event) {
        this.isActive = false;
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        this.isActive = true;
    }
    protected Account() {  }
}
