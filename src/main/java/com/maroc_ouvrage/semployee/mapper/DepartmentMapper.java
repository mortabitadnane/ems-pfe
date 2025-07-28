package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.DepartmentDTO;
import com.maroc_ouvrage.semployee.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    // Map Entity → DTO
    @Mapping(source = "id", target = "departmentId")
    DepartmentDTO toDto(Department department);

    // Map DTO → Entity
    @Mapping(source = "departmentId", target = "id")
    Department toEntity(DepartmentDTO departmentDTO);

    // Update existing Entity with DTO data
    @Mapping(source = "departmentId", target = "id")
    void updateEntityFromDTO(DepartmentDTO dto, @MappingTarget Department entity);

    // Map list of Entities to list of DTOs
    List<DepartmentDTO> toDtoList(List<Department> departments);
}
