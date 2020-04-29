package com.software.account.query;

import com.software.account.aggregate.DeviceStatus;
import com.software.account.aggregate.DeviceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name = "device")
public class RegisteredDevice {
    @Id
    private String deviceId;
    private String name;
    private DeviceStatus deviceStatus;
    private DeviceType deviceType;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable=false)
    private RegisteredAccount account;

    public RegisteredDevice(String deviceId, String name, DeviceType deviceType) {
        this.deviceId = deviceId;
        this.name = name;
        this.deviceType = deviceType;
        deviceStatus = DeviceStatus.PENDING_CONFIRMATION;
    }

    public void setDeviceConfirmed() {
        deviceStatus = DeviceStatus.CONFIRMED;
    }

    public void setDeviceRejected() {
        deviceStatus = DeviceStatus.REJECTED;
    }
}
