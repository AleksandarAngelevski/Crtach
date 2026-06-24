package org.example.crtachbackend.mapper;

import org.example.crtachbackend.dto.UserDto;
import org.example.crtachbackend.dto.UserRegistrationDto;
import org.example.crtachbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

/**
 * A Mapper interface
 * used to map users
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Method for mapping
     * user entity to user dto
     *
     * @param user - the user param that's
     *             mapped
     *
     * @return - returns a user dto
     */
    UserDto userToUserDto(User user);

    /**
     * Method for mapping
     * user dto to user entity
     *
     * @param userDto - the user dto
     *                param that's mapped
     *
     * @return - returns a user entity
     */
    User userDtoToUser(UserDto userDto);

    /**
     * Method for mapping
     * user registration dto
     * to user entity
     *
     * @param userRegistrationDto - the user
     *                            registration dto
     *                            param that's mapped
     *
     * @return - returns a user entity
     */
    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "userDtoBirthDateToUserBirthDate")
    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);

    /**
     *
     * A named function that takes the birthDate and converts
     * it to LocalDate
     *
     * @param birthDate - the parameter that's converted
     * @return - returns a LocalDate birthDate
     */
    @Named("userDtoBirthDateToUserBirthDate")
    static LocalDate userDtoBirthDateToUserBirthDate(String birthDate) {

        String[] dateParts = birthDate.split("/");

        int year = Integer.parseInt(dateParts[2]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[0]);

        return LocalDate.of(year, month, day);
    }

    @Named("userBirthDateToUserDto")
    static String userBirthDateToUserDto(LocalDate birthDate) {

        return birthDate.toString();
    }
}
