package juste.backend.mappers;

import juste.backend.dtos.responses.UserResponse;
import juste.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author PAKOU Komi Juste
 * @since 1/16/26
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convertit un User en UserResponse.
     */
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserResponse toResponse(User user);
}
