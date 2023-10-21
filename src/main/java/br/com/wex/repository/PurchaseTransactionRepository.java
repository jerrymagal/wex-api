package br.com.wex.repository;

import br.com.wex.entity.PurchaseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransaction, Long> {
    Optional<PurchaseTransaction> findByUuid(UUID uuid);
}
