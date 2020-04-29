package com.software.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDeactivatedEvent {
    private final String id;
}
