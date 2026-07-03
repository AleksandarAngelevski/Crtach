package org.example.crtachbackend.repository;

import org.example.crtachbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A user repository interface
 * used for interacting with the user db
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method that gets a user by username
     *
     * @param username - the username param
     *                 used for searching the
     *                 db
     *
     * @return - returns a user if it exists
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Method that gets a user by email
     *
     * @param email - the email param used
     *              for searching the db
     *
     * @return - returns a user if it exists
     */
    Optional<User> findUserByEmail(String email);
}
