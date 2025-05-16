package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "SYSTEM_CONFIG")
public class SystemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SYSTEM_CONFIG_SEQ")
    @SequenceGenerator(name = "SYSTEM_CONFIG_SEQ", sequenceName = "SYSTEM_CONFIG_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DATA_TYPE")
    private String dataType;
    @Column(name = "STR_VAL")
    private String strVal;
    @Column(name = "INT_VAL")
    private Integer intVal;
    @Column(name = "DOUBLE_VAL")
    private Double doubleVal;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;
    @Column(name = "MODIFY_BY")
    private LocalDateTime modifyBy;
    @Column(name = "STATUS")
    private Integer status;
}
