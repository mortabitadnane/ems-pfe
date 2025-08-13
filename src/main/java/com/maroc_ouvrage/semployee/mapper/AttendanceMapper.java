package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.AttendanceDTO;
import com.maroc_ouvrage.semployee.dto.AttendanceRequestDTO;
import com.maroc_ouvrage.semployee.model.Attendance;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // From entity to DTO
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(target = "employeeName", ignore = true) // we set this manually
    AttendanceDTO toDTO(Attendance attendance);

    List<AttendanceDTO> toDTOs(List<Attendance> attendances);

    // From request DTO to entity
    @Mapping(target = "employee", ignore = true)
    Attendance toEntity(AttendanceRequestDTO dto);

    @AfterMapping
    default void setEmployeeName(@MappingTarget AttendanceDTO dto, Attendance attendance) {
        if (attendance.getEmployee() != null) {
            String firstName = attendance.getEmployee().getFirstName();
            String lastName = attendance.getEmployee().getLastName();
            dto.setEmployeeName(firstName + " " + lastName);
        }
    }
}

