package com.trade.tradelicense.domain.repository;

import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.valueobjects.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Domain repository interface for {@link User} entities.
 */
public interface IUserRepository {

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the user UUID; must not be {@code null}
     * @return an {@link Optional} containing the user, or empty if not found
     */
    Optional<User> findById(UUID id);

    /**
     * Finds a user by their e-mail address.
     *
     * @param email the unique e-mail address; must not be {@code null}
     * @return an {@link Optional} containing the user, or empty if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Returns all users that hold the given role.
     *
     * @param role the {@link UserRole} to filter by; must not be {@code null}
     * @return a (possibly empty) list of matching users
     */
    List<User> findByRole(UserRole role);
}
