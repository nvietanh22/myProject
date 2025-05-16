package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "PARTNER_MANAGEMENT")
public class PartnerManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTNER_MANAGEMENT_SEQ")
    @SequenceGenerator(name = "PARTNER_MANAGEMENT_SEQ", sequenceName = "PARTNER_MANAGEMENT_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "PARTNER_NAME")
    private String partnerName;
    @Column(name = "PARTNER_CODE")
    private String partnerCode;
    @Column(name = "TAX_CODE")
    private String taxCode;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
    @Column(name = "MODIFY_BY")
    private String modifyBy;
    @Column(name = "ADDITIONAL_INFO")
    private String additionalInfo;

    @OneToMany(mappedBy = "partnerManagement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnerAttachment> partnerAttachments = new ArrayList<>();

}
