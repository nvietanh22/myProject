package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CONSENT_TEMPLATE")
public class ConsentTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONSENT_TEMPLATE_SEQ")
    @SequenceGenerator(name = "CONSENT_TEMPLATE_SEQ", sequenceName = "CONSENT_TEMPLATE_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "TEMP_NAME")
    private String tempName;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "LEVEL1")
    private String level1;
    @Column(name = "LEVEL2")
    private String level2;
    @Column(name = "CHANNEL")
    private String channel;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
    @Column(name = "STATUS")
    private String status;
}
