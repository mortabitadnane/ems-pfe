package com.maroc_ouvrage.semployee.repo;

import com.maroc_ouvrage.semployee.model.Employee;
import com.maroc_ouvrage.semployee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByCin(String cin);
    Optional<Employee> findByUser(User user);
}
