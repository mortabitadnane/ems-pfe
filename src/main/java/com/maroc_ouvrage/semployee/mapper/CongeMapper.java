package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.model.Conge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface CongeMapper{

    @Mapping(source = "id", target = "congeId")
    @Mapping(source = "employee.cin", target = "employeeCin")
    CongeDTO toDto(Conge conge);

    @Mapping(source = "congeId", target = "id") // Map DTO.congeId → entity.id
    @Mapping(source = "employeeCin", target = "employee.cin")
    Conge toEntity(CongeDTO congeDTO);

    List<CongeDTO> toDtoList(List<Conge> conges);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    void updateEntityFromDto(CongeDTO congeDTO, @MappingTarget Conge conge);
}

