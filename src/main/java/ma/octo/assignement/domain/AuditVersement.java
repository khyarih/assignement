package ma.octo.assignement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.octo.assignement.util.EventType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "AUDIT_VIREMENT")
public class AuditVersement {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 100)
  private String message;

  @Enumerated(EnumType.STRING)
  private EventType eventType;
}
