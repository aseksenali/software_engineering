package com.software.account.query;

import com.software.account.aggregate.Device;
import com.software.account.aggregate.DeviceStatus;
import com.software.account.aggregate.DeviceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name = "account")
public class RegisteredAccount {
    @Id
    private String accountId;
    private String username;
    private boolean isActive;
    @OneToMany(mappedBy = "account")
    private List<RegisteredDevice> devices;

    public RegisteredAccount(String accountId, String username) {
        this.accountId = accountId;
        this.username = username;
        this.isActive = true;
        this.devices = new ArrayList<>();
    }

    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }
}
