package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.AppreciationDto;
import com.skilldrill.registration.model.Appreciation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppreciationMapper {

    AppreciationDto toDto(Appreciation appreciation);

    Appreciation toAppreciation(AppreciationDto appreciationDto);
}
