package com.software.account.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ActivateAccountCommand {
    @TargetAggregateIdentifier
    private final String id;
}
