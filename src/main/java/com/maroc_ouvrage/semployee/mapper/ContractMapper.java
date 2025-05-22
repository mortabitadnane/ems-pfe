package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.ContractDTO;
import com.maroc_ouvrage.semployee.model.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    // Map ContractDTO to Contract entity
    @Mapping(source = "salary", target = "salary")
    @Mapping(source = "contractType", target = "contractType")
    @Mapping(source = "jobTitle", target = "jobTitle")
    @Mapping(source = "benefits", target = "benefits")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    Contract toEntityFromDTO(ContractDTO contractDTO); 

    // Map Contract entity to ContractDTO
    @Mapping(source = "salary", target = "salary")
    @Mapping(source = "contractType", target = "contractType")
    @Mapping(source = "jobTitle", target = "jobTitle")
    @Mapping(source = "benefits", target = "benefits")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    ContractDTO toDTOFromEntity(Contract contract);

    // Update Contract entity from ContractDTO
    @Mapping(source = "salary", target = "salary")
    @Mapping(source = "contractType", target = "contractType")
    @Mapping(source = "jobTitle", target = "jobTitle")
    @Mapping(source = "benefits", target = "benefits")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    void updateEntityFromDTO(ContractDTO contractDTO, @MappingTarget Contract contract);
}



