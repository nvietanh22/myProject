package vn.lottefinance.pdms_core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "TOKEN")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TOKEN_SEQ")
    @SequenceGenerator(name = "TOKEN_SEQ", sequenceName = "TOKEN_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "TOKEN")
    private String token;
    @Column(name = "USER_ID")
    private String userID;
    @Column(name = "STATUS")
    private String Status;
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "ENDDATE")
    private LocalDateTime endDate;
}

