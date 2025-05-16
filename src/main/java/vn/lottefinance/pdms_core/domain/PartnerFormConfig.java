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
@Table(name = "PARTNER_FORM_CONFIG")
public class PartnerFormConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTNER_FORM_CONFIG_SEQ")
    @SequenceGenerator(name = "PARTNER_FORM_CONFIG_SEQ", sequenceName = "PARTNER_FORM_CONFIG_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "FIELD_CODE")
    private String fieldCode;
    @Column(name = "FIELD_LABEL")
    private String fieldLabel;
    @Column(name = "IS_REQUIRED")
    private String isRequired; // Y|N
    @Column(name = "IS_SHOW")
    private String isShow; // Y|N
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
    @Column(name = "MODIFY_BY")
    private String modifyBy;
    @Column(name = "DATA_TYPE")
    private String dataType;
    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

}
