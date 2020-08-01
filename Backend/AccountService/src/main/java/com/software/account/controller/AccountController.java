package com.software.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.account.aggregate.Account;
import com.software.account.aggregate.DeviceType;
import com.software.account.command.ConfirmDeviceCommand;
import com.software.account.command.CreateAccountCommand;
import com.software.account.command.RegisterDeviceCommand;
import com.software.account.query.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    @Value("${security.jwt.secret}")
    private String jwtSecret;
    @Value("${security.jwt.header}")
    private String jwtHeader;
    @Value("${security.jwt.prefix}")
    private String jwtPrefix;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final EventStore eventStore;
    private final Logger logger;

    public AccountController(CommandGateway commandGateway, QueryGateway queryGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.eventStore = eventStore;
        logger = LoggerFactory.getLogger(AccountController.class);
    }

    @PostMapping
    public String createAccount(HttpServletRequest request) {
        String accountId = UUID.randomUUID().toString();
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()) {
            String e = enumeration.nextElement();
            logger.info(e);
        }
        String header = request.getHeader(jwtHeader);
        String token = header.replace(jwtPrefix, "");
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        commandGateway.send(new CreateAccountCommand(accountId, claims.getSubject()));
        return accountId;
    }

    @GetMapping("/devices")
    public List<RegisteredDevice> getAllDevices() {
        return queryGateway.query(new FindAllRegisteredDevicesQuery(),
                ResponseTypes.multipleInstancesOf(RegisteredDevice.class)).join();
    }

    @GetMapping("/admin/events")
    public List<Object> listAllEvents(@RequestParam String deviceId) {
        return eventStore.readEvents(deviceId).asStream().map(Message::getPayload).collect(Collectors.toList());
    }

    @PostMapping("/devices")
    public RegisteredDevice registerDevice(@RequestBody Map<String, String> params, HttpServletRequest request) throws ExecutionException, InterruptedException {
        String header = request.getHeader(jwtHeader);
        String token = header.replace(jwtPrefix, "");
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        RegisteredAccount account = queryGateway.query(new FindAccountByUsernameQuery(claims.getSubject()), RegisteredAccount.class).get();
        DeviceType deviceType = DeviceType.valueOf(params.get("type"));
        String deviceId = UUID.randomUUID().toString();
        commandGateway.send(new RegisterDeviceCommand(account.getUsername(), claims.getSubject(), params.get("name"), deviceType));
        commandGateway.send(new ConfirmDeviceCommand(deviceId));
        return new RegisteredDevice(deviceId, params.get("name"), deviceType);
    }
}

