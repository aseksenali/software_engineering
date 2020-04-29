package com.software.account.aggregate;

import com.software.account.command.ConfirmDeviceCommand;
import com.software.account.command.RegisterDeviceCommand;
import com.software.account.command.RegisterSmartwatchCommand;
import com.software.account.command.RejectDeviceCommand;
import com.software.account.event.DeviceConfirmedEvent;
import com.software.account.event.DeviceRegisteredEvent;
import com.software.account.event.DeviceRejectedEvent;
import com.software.account.event.SmartwatchRegisteredEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class SmartWatch extends Device {
    @CommandHandler
    public SmartWatch(RegisterSmartwatchCommand command) {
        AggregateLifecycle.apply(new SmartwatchRegisteredEvent(command.getDeviceId(), command.getDeviceName()));
    }

    @CommandHandler
    public void handle(ConfirmDeviceCommand command) {
        if (this.confirmDevice()) {
            AggregateLifecycle.apply(new DeviceConfirmedEvent(command.getId()));
        } else {
            this.getCommandGateway().send(new RejectDeviceCommand(command.getId()));
        }
    }

    @CommandHandler
    public void handle(RejectDeviceCommand command) {
        AggregateLifecycle.apply(new DeviceRejectedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(DeviceConfirmedEvent event) {
        this.setStatus(DeviceStatus.CONFIRMED);
    }

    @EventSourcingHandler
    public void on(DeviceRejectedEvent event) {
        this.setStatus(DeviceStatus.REJECTED);
    }
    @EventSourcingHandler
    public void on(SmartwatchRegisteredEvent event) {
        this.setDeviceId(event.getDeviceId());
        this.setDeviceName(event.getDeviceName());
        this.setStatus(DeviceStatus.PENDING_CONFIRMATION);
    }

    @Override
    public boolean confirmDevice() {
        this.setStatus(DeviceStatus.CONFIRMED);
        return true;
    }
}
