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
@Table(name = "PARTNER_MANAGEMENT")
public class PartnerAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTNER_ATTACHMENT_SEQ")
    @SequenceGenerator(name = "PARTNER_ATTACHMENT_SEQ", sequenceName = "PARTNER_ATTACHMENT_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_URL")
    private String fileUrl;
    @Column(name = "TAX_CODE")
    private String taxCode;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_ID")
    private PartnerManagement partnerManagement;
}
