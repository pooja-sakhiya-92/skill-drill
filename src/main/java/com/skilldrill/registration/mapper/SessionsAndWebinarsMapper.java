package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.SessionsAndWebinarsDto;
import com.skilldrill.registration.model.SessionsAndWebinars;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionsAndWebinarsMapper {
    SessionsAndWebinarsDto toDto(SessionsAndWebinars sessionsAndWebinars);

    SessionsAndWebinars toSessionsAndWebinars(SessionsAndWebinarsDto sessionsAndWebinarsDto);
}
