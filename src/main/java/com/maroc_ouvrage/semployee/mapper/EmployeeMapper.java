package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;
import com.maroc_ouvrage.semployee.model.Contract;
import com.maroc_ouvrage.semployee.model.Employee;
import org.mapstruct.MappingTarget;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Map Employee and Contract to EmployeecontractDTO
    @Mapping(source = "employee.cin", target = "cin")
    @Mapping(source = "employee.firstName", target = "firstname")
    @Mapping(source = "employee.lastName", target = "lastname")
    @Mapping(source = "employee.position", target = "position")
    @Mapping(source = "employee.rib", target = "rib")
    @Mapping(source = "contract.salary", target = "salary")
    @Mapping(source = "contract.contractType", target = "contractType")
    @Mapping(source = "contract.startDate", target = "startDate")
    @Mapping(source = "contract.endDate", target = "endDate")
    EmployeecontractDTO toDto(Employee employee, Contract contract);

    @Mapping(source = "cin", target = "cin")
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "rib", target = "rib")
    void toEmployeeEntity(EmployeecontractDTO dto, @MappingTarget Employee employee);

    @Mapping(source = "salary", target = "salary")
    @Mapping(source = "contractType", target = "contractType")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    void toContractEntity(EmployeecontractDTO dto, @MappingTarget Contract contract);

}
