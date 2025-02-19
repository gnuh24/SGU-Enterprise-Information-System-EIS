package BackEnd.repositories;

import BackEnd.entities.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByCodeAndCategory(String code, OTP.Category category); // Find OTP by its code

    Optional<OTP> findByAccountIdAndCategory(Long accountId, OTP.Category category); // Find OTP by account and category

    void deleteByCodeAndCategory(String code, OTP.Category category);

}

