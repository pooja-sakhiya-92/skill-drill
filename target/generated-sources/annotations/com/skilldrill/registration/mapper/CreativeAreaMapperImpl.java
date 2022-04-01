package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.CreativeAreaDto;
import com.skilldrill.registration.enums.CreativeSkills;
import com.skilldrill.registration.model.CreativeArea;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-22T10:40:51+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_311 (Oracle Corporation)"
)
@Component
public class CreativeAreaMapperImpl implements CreativeAreaMapper {

    @Override
    public CreativeAreaDto toDto(CreativeArea creativity) {
        if ( creativity == null ) {
            return null;
        }

        CreativeAreaDto creativeAreaDto = new CreativeAreaDto();

        creativeAreaDto.setId( creativity.getId() );
        if ( creativity.getCreativeSkill() != null ) {
            creativeAreaDto.setCreativeSkill( creativity.getCreativeSkill().name() );
        }
        creativeAreaDto.setAchievements( creativity.getAchievements() );
        creativeAreaDto.setSpecialization( creativity.getSpecialization() );
        creativeAreaDto.setUser( creativity.getUser() );

        return creativeAreaDto;
    }

    @Override
    public CreativeArea toCreativeArea(CreativeAreaDto creativeAreaDto) {
        if ( creativeAreaDto == null ) {
            return null;
        }

        CreativeArea creativeArea = new CreativeArea();

        creativeArea.setId( creativeAreaDto.getId() );
        if ( creativeAreaDto.getCreativeSkill() != null ) {
            creativeArea.setCreativeSkill( Enum.valueOf( CreativeSkills.class, creativeAreaDto.getCreativeSkill() ) );
        }
        creativeArea.setAchievements( creativeAreaDto.getAchievements() );
        creativeArea.setSpecialization( creativeAreaDto.getSpecialization() );
        creativeArea.setUser( creativeAreaDto.getUser() );

        return creativeArea;
    }
}
