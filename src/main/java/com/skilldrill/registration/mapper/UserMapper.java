package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends ForeignKeyMapper {

    UserDto toDto(User user);

    User toUser(UserDto dto);
}
