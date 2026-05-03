package com.example.homestaymanager.mapper;

import com.example.homestaymanager.dto.request.UpdateEmployee;
import com.example.homestaymanager.model.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
     void updateEmployeeFromDto(
            UpdateEmployee dto,
            @MappingTarget Employee entity
    );
}
