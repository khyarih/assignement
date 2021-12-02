package ma.octo.assignement.repository;

import ma.octo.assignement.domain.AuditVersement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditVersementRepository extends JpaRepository<AuditVersement,Long> {
}
