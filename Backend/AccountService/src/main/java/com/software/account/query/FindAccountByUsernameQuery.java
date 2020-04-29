package com.software.account.query;

import lombok.Data;

@Data
public class FindAccountByUsernameQuery {
    private final String username;
}
