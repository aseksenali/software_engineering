package com.software.account;

import com.software.account.aggregate.Device;
import com.software.account.aggregate.PC;
import com.software.account.aggregate.SmartPhone;
import com.software.account.aggregate.SmartWatch;
import org.axonframework.config.AggregateConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
