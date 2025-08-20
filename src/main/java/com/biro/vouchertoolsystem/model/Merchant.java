package com.biro.vouchertoolsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_english", nullable = false)
    private String nameEnglish;

    @Column(name = "name_arabic", nullable = false)
    private String nameArabic;

    @Column(name = "LOGO_URL", length = 1024)
    private String logoUrl;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive = true;


    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = false)
    private Date updatedAt;

    @Column(name = "DELETED_AT")
    private Date deletedAt;

}
