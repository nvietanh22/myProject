package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CAMPAIN_NOTI")
public class CampainNoti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CAMPAIN_NOTI_SEQ")
    @SequenceGenerator(name = "CAMPAIN_NOTI_SEQ", sequenceName = "CAMPAIN_NOTI_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "TOTAL_RECORD")
    private Long totalRecord;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
    @Column(name = "MODIFY_BY")
    private String modifyBy ;
    @Column(name = "STATUS")
    private String status;

    @OneToMany(mappedBy = "campainNoti", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampainNotiDetail> details = new ArrayList<>();
}
