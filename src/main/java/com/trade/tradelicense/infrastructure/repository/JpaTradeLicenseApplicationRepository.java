package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA-backed implementation of {@link ITradeLicenseApplicationRepository}.
 *
 * <p>Delegates all persistence operations to an inner Spring Data JPA interface
 * ({@link SpringDataTradeLicenseApplicationRepository}), keeping the domain
 * repository contract clean of Spring Data specifics.
 */
@Repository
@RequiredArgsConstructor
public class JpaTradeLicenseApplicationRepository implements ITradeLicenseApplicationRepository {

    private final SpringDataTradeLicenseApplicationRepository springRepo;

    @Override
    public Optional<TradeLicenseApplication> findById(UUID id) {
        return springRepo.findById(id);
    }

    @Override
    public List<TradeLicenseApplication> findByApplicantId(UUID applicantId) {
        return springRepo.findByApplicant_Id(applicantId);
    }

    @Override
    public List<TradeLicenseApplication> findByStatus(ApplicationStatus status) {
        return springRepo.findByStatus(status);
    }

    @Override
    public TradeLicenseApplication save(TradeLicenseApplication application) {
        return springRepo.save(application);
    }

    @Override
    public void deleteById(UUID id) {
        springRepo.deleteById(id);
    }

    /**
     * Inner Spring Data JPA repository interface. Spring Data will generate the
     * implementation at application startup via classpath scanning.
     */
    interface SpringDataTradeLicenseApplicationRepository
            extends JpaRepository<TradeLicenseApplication, UUID> {

        /**
         * Finds all applications whose applicant has the given UUID.
         *
         * @param applicantId the UUID of the applicant user
         * @return matching applications
         */
        @Query("SELECT a FROM TradeLicenseApplication a WHERE a.applicant.id = :applicantId")
        List<TradeLicenseApplication> findByApplicant_Id(@Param("applicantId") UUID applicantId);

        /**
         * Finds all applications in the given status.
         *
         * @param status the target {@link ApplicationStatus}
         * @return matching applications
         */
        List<TradeLicenseApplication> findByStatus(ApplicationStatus status);
    }
}
