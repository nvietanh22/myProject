package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CAMPAIN_NOTI_DETAIL")
public class CampainNotiDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CAMPAIN_NOTI_DETAIL_SEQ")
    @SequenceGenerator(name = "CAMPAIN_NOTI_DETAIL_SEQ", sequenceName = "CAMPAIN_NOTI_DETAIL_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "CIF_NUMBER")
    private String cifNumber;
    @Column(name = "IDCARD_NUMBER")
    private String idCardNumber;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NOTI_CHANNEL")
    private String notiChannel;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
    @Column(name = "MODIFY_BY")
    private String modifyBy;
    @Column(name = "STATUS")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIN_NOTI_ID")
    private CampainNoti campainNoti;
}
