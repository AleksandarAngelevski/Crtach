package org.example.crtachbackend.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.crtachbackend.dto.UserDto;
import org.example.crtachbackend.dto.UserLoginDto;
import org.example.crtachbackend.dto.UserRegistrationDto;
import org.example.crtachbackend.mapper.UserMapper;
import org.example.crtachbackend.model.User;
import org.example.crtachbackend.repository.UserRepository;
import org.example.crtachbackend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * User Service implementation class
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Method used for creating a new user
     *
     * @param userRegistrationDto - the user
     *                            registration dto
     *                            param used for
     *                            creating a user
     *
     * @return - returns the created user dto
     */
    @Transactional
    @Override
    public UserDto createUser(UserRegistrationDto userRegistrationDto) {

        User user = userMapper.userRegistrationDtoToUser(userRegistrationDto);

        user.setPassword(passwordEncoder(userRegistrationDto.getPassword()));

        return userMapper.userToUserDto(userRepository.save(user));
    }

    /**
     * Method that updates a user
     *
     * @param userId - user id param used
     *               for finding the user that's
     *               supposed to be updated
     *
     * @param userDto - the user dto param used for
     *                updating the user
     *
     * @return - returns the updated user dto
     */
    @Transactional
    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {

        if (userId == null) {

            throw new IllegalArgumentException("userId cannot be null");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(user.getPassword());


        return userMapper.userToUserDto(userRepository.save(user));
    }

    /**
     * Method used for getting a user by id
     *
     * @param id - the id param used for finding
     *           the user
     *
     * @return - returns the found user dto
     */
    @Override
    public UserDto getUserById(Long id) {

            if (id == null) {
                throw new IllegalArgumentException("id cannot be null");
            }

            return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!")));
    }

    /**
     * Method that deletes a user by id
     *
     * @param id - id param used for deleting
     *           a user
     */
    @Transactional
    @Override
    public void deleteUserById(Long id) {

            if (id == null) {
                throw new IllegalArgumentException("id cannot be null");
            }

            userRepository.deleteById(id);
    }

    /**
     * Method used for authenticating a user
     *
     * @param userLoginDto - the user login dto
     *                     param used for logging in
     *                     the user
     *
     * @return - returns the authenticated user
     */
    @Override
    public User authenticate(UserLoginDto userLoginDto) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getUsername(),
                            userLoginDto.getPassword()
                    )
            );

            return userRepository.findUserByUsername(userLoginDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    /**
     * A method that encodes the password before
     * saving it to the db
     *
     * @param password - the password parameter
     *                 that is to be encoded
     * @return - returns the encoded password
     */
    private String passwordEncoder(String password) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }
}
