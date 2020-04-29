package com.software.account.repository;

import com.software.account.query.RegisteredDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<RegisteredDevice, String> {
}
