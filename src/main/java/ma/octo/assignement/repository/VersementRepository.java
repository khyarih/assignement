package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Versement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersementRepository extends JpaRepository<Versement,Long> {
}
