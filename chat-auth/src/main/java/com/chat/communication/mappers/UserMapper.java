package com.chat.communication.mappers;

import com.chat.communication.domains.User;
import com.chat.communication.dto.UserDto;
import com.chat.communication.dto.UserLoginDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


//    @Mapping(target = "Role", source = "Role")
    UserDto userToUserDto(User user);

    @InheritInverseConfiguration
    UserLoginDto userToUserLoginDto(User user);
    @InheritInverseConfiguration
    User userDtoToUser(UserDto userDto);


}

