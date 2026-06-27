package org.example.crtachbackend.service;

import org.example.crtachbackend.dto.UserDto;
import org.example.crtachbackend.dto.UserLoginDto;
import org.example.crtachbackend.dto.UserRegistrationDto;
import org.example.crtachbackend.model.User;

/**
 * The user service interface
 */
public interface UserService {

    /**
     * Method used for creating a
     * new user
     *
     * @param userRegistrationDto - the user
     *                            registration dto
     *                            param used for
     *                            creating a user
     *
     * @return - returns a user dto
     */
    UserDto createUser(UserRegistrationDto userRegistrationDto);

    /**
     * Method used for updating a user
     *
     * @param userId - user id param used
     *               for finding the user that's
     *               supposed to be updated
     *
     * @param userDto - the user dto param used for
     *                updating the user
     *
     * @return - returns a user dto
     */
    UserDto updateUser(Long userId, UserDto userDto);

    /**
     * Method used for getting a user by id
     *
     * @param id - the id param used for finding
     *           the user
     *
     * @return - returns a user dto
     */
    UserDto getUserById(Long id);

    /**
     * Method used for deleting a user
     *
     * @param id - id param used for deleting
     *           a user
     */
    void deleteUserById(Long id);

    /**
     * Method used for authenticating
     * a user
     *
     * @param userLoginDto - the user login dto
     *                     param used for logging in
     *                     the user
     *
     * @return - return's a user
     */
    User authenticate(UserLoginDto userLoginDto);
}
