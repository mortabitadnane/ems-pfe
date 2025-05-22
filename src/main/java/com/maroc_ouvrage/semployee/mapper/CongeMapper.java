package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.model.Conge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface CongeMapper{

    @Mapping(source = "employee.cin", target = "employeeCin")
    CongeDTO toDto(Conge conge);

    @Mapping(source = "employeeCin", target = "employee.cin")
    Conge toEntity(CongeDTO congeDTO);

    List<CongeDTO> toDtoList(List<Conge> conges);

    void updateEntityFromDto(CongeDTO congeDTO, @MappingTarget Conge conge);
}

