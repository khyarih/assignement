package ma.octo.assignement.repository;

import ma.octo.assignement.domain.AuditVirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditVirementRepository extends JpaRepository<AuditVirement, Long> {
}
