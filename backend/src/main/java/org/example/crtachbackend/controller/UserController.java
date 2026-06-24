package org.example.crtachbackend.controller;

import jakarta.validation.Valid;
import org.example.crtachbackend.dto.UserDto;
import org.example.crtachbackend.dto.UserRegistrationDto;
import org.example.crtachbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class used for the user
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method that gets a user by id
     *
     * @param id - the user id param used
     *           for searching for the user
     *
     * @return - returns the user if it exists
     *          and status 200 ok
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){

        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Method that creates a user
     *
     * @param userRegistrationDto - the user registration dto
     *                            param used to create the
     *                            user
     *
     * @return - returns the created user and status 201 created
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto){

        return new ResponseEntity<>(userService.createUser(userRegistrationDto), HttpStatus.CREATED);
    }

    /**
     * Method that updates a user by id
     *
     * @param id - the user id param used
     *           for searching for the user
     *           that's supposed to be updated
     *
     * @param userDto - the user dto param used
     *                for updating the user
     *
     * @return - returns the updated user and status
     *          201 created
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto){

        return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.CREATED);
    }

    /**
     * Method that deletes a user by id
     *
     * @param id - the user id param used for
     *           searching for the user that's
     *           supposed to be deleted
     *
     * @return - returns no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
}
