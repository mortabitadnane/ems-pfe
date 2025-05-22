package com.maroc_ouvrage.semployee.repo;


import com.maroc_ouvrage.semployee.model.Contract;
import com.maroc_ouvrage.semployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Contract findByEmployee(Employee employee);
    Optional<Contract> findByEmployeeId(Long employeeId);
}


