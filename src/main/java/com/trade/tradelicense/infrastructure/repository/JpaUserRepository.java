package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.repository.IUserRepository;
import com.trade.tradelicense.domain.valueobjects.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA-backed implementation of {@link IUserRepository}.
 *
 * <p>Delegates all persistence operations to the inner Spring Data JPA interface
 * {@link SpringDataUserRepository}.
 */
@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements IUserRepository {

    private final SpringDataUserRepository springRepo;

    @Override
    public Optional<User> findById(UUID id) {
        return springRepo.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springRepo.findByEmail(email);
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return springRepo.findByRole(role);
    }

    /**
     * Inner Spring Data JPA interface for {@link User} persistence.
     */
    interface SpringDataUserRepository extends JpaRepository<User, UUID> {

        /**
         * Finds a user by their unique e-mail address.
         *
         * @param email the e-mail address to look up
         * @return the matching user, or empty if not found
         */
        Optional<User> findByEmail(String email);

        /**
         * Returns all users holding the specified role.
         *
         * @param role the {@link UserRole} to filter by
         * @return matching users
         */
        List<User> findByRole(UserRole role);
    }
}
