package com.employee.records.employee_records_system.repository;
import com.employee.records.employee_records_system.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppRoleRepository extends JpaRepository<UserRole,Long> {
}
