package org.example.crtachbackend.repository;

import org.example.crtachbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A user repository interface
 * used for interacting with the user db
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
