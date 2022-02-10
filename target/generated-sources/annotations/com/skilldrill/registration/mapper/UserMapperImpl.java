package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.model.enums.Department;
import com.skilldrill.registration.model.enums.Roles;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-10T15:56:00+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_192 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );
        userDto.setPhone( user.getPhone() );
        userDto.setPassword( user.getPassword() );
        userDto.setPosition( user.getPosition() );
        if ( user.getDepartment() != null ) {
            userDto.setDepartment( user.getDepartment().name() );
        }
        if ( user.getRole() != null ) {
            userDto.setRole( user.getRole().name() );
        }
        userDto.setActive( user.getActive() );
        userDto.setOtp( user.getOtp() );
        userDto.setUpdateFlag( user.getUpdateFlag() );

        return userDto;
    }

    @Override
    public User toUser(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setPassword( dto.getPassword() );
        user.setPosition( dto.getPosition() );
        if ( dto.getDepartment() != null ) {
            user.setDepartment( Enum.valueOf( Department.class, dto.getDepartment() ) );
        }
        if ( dto.getRole() != null ) {
            user.setRole( Enum.valueOf( Roles.class, dto.getRole() ) );
        }
        user.setActive( dto.getActive() );
        user.setOtp( dto.getOtp() );
        user.setUpdateFlag( dto.getUpdateFlag() );

        return user;
    }
}
