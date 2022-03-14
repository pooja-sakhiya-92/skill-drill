package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.BadgesDto;
import com.skilldrill.registration.model.Badges;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BadgeMapper extends ForeignKeyMapper {

    BadgesDto toDto(Badges badges);

    Badges toBadges(BadgesDto badgesDto);
}
