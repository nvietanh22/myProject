package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.*;
import vn.lottefinance.pdms_core.enums.ConsentSourceEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CUSTOMER_CONSENT")
public class CustomerConsent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CUSTOMER_CONSENT_SEQ")
    @SequenceGenerator(name = "CUSTOMER_CONSENT_SEQ", sequenceName = "CUSTOMER_CONSENT_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "IDCARD_NUMBER")
    private String idCardNumber;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "REF_ID")
    private String refId;
    @Column(name = "CIF_NUMBER")
    private String cifNumber;
    @Column(name = "CREATED_BY")
    private String createBy;
    @Column(name = "MODIFY_BY")
    private String modifyBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
    @Column(name = "DOB")
    private LocalDateTime dob;
    @Column(name = "CHANNEL")
    private String channel;
    @Column(name = "ISSUED_DATE")
    private LocalDateTime issuedDate;
    @Column(name = "ISSUED_PLACE")
    private String issuedPlace;
    @Column(name = "CUS_COMMENT")
    private String cusComment;
    @Column(name = "REFERENCE")
    private String reference;
    @Column(name = "EVIDENCE")
    private String evidence;

    @OneToMany(mappedBy = "customerConsent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerConsentLog> customerConsentLogs = new ArrayList<>();
}
