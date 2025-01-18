package com.employee.records.employee_records_system.repository;
import com.employee.records.employee_records_system.model.AppUser;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);
}
