package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.repository.IUserRepository;
import com.trade.tradelicense.domain.valueobjects.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA-backed implementation of {@link IUserRepository}.
 *
 * <p>Extends both {@link JpaRepository} and the domain interface so this single
 * Spring Data repository can be injected wherever {@link IUserRepository} is required.
 */
@Repository
public interface JpaUserRepository
        extends JpaRepository<User, UUID>, IUserRepository {

    /**
     * {@inheritDoc}
     *
     * <p>Derived from the {@code email} field name via Spring Data naming conventions.
     */
    @Override
    Optional<User> findByEmail(String email);

    /**
     * {@inheritDoc}
     *
     * <p>Derived from the {@code role} field name via Spring Data naming conventions.
     */
    @Override
    List<User> findByRole(UserRole role);
}
