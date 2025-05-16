package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CUSTOMER_CONSENT_SERVICE")
public class CustomerConsentService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CUSTOMER_CONSENT_SERVICE_SEQ")
    @SequenceGenerator(name = "CUSTOMER_CONSENT_SERVICE_SEQ", sequenceName = "CUSTOMER_CONSENT_SERVICE_SEQ", allocationSize = 1)
    private Long id;
    private Long customerConsentId;
    private Long serviceId;
    private Long sourceData;
    private LocalDateTime createdDate;
    private String createdBy;
}
