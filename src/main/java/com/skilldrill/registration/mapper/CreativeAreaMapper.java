package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.CreativeAreaDto;
import com.skilldrill.registration.model.CreativeArea;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreativeAreaMapper {
    CreativeAreaDto toDto(CreativeArea creativity);

    CreativeArea toCreativeArea(CreativeAreaDto creativeAreaDto);
}
