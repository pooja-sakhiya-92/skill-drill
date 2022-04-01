package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.RatingsDto;
import com.skilldrill.registration.model.Ratings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingsMapper extends ForeignKeyMapper {

    RatingsDto toDto(Ratings ratings);

    Ratings toRatings(RatingsDto ratingsDto);

}
