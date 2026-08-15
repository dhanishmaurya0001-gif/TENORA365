package com.tenora365.repository;

import com.tenora365.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    List<FeePayment> findBySchoolIdOrderByIdDesc(Long id);

    Optional<FeePayment> findByIdAndSchoolId(
            Long id,
            Long schoolId
    );

    @Query("""
            SELECT COALESCE(SUM(f.amount), 0)
            FROM FeePayment f
            WHERE f.school.id = :id
              AND f.status = 'PAID'
            """)
    BigDecimal sumPaid(@Param("id") Long id);
}