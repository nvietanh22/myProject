package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CUSTOMER_CONSENT_LOG")
public class CustomerConsentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CUSTOMER_CONSENT_LOG_SEQ")
    @SequenceGenerator(name = "CUSTOMER_CONSENT_LOG_SEQ", sequenceName = "CUSTOMER_CONSENT_LOG_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "SOURCE_CHANNEL")
    private String sourceChannel;
    @Column(name = "NOTI_CHANNEL")
    private String notiChannel;
    @Column(name = "NOTI_CONTENT")
    private String notiContent;
    @Column(name = "CREATED_BY")
    private String createBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "STATUS")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCARD_NUMBER")
    private CustomerConsent customerConsent;
}
