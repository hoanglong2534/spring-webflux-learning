package com.webflux.demo.mapper;

import com.webflux.demo.dto.request.UserRequest;
import com.webflux.demo.dto.response.UserResponse;
import com.webflux.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(UserRequest request);

}
