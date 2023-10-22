package com.example.bontaniq.repository;

import com.example.bontaniq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checks the database if a user with given emailAddress exists.
     * @param emailAddress
     * @return A User if it finds a match,
     *         null otherwise.
     */
    User findUserByEmailAddress(String emailAddress);
}
