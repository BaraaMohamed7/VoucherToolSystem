package com.biro.vouchertoolsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "OFFERS")
@EntityListeners(AuditingEntityListener.class)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "offer_categories",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> offerCategories;

    @OneToMany(mappedBy = "offer")
    private List<Product> products;

    @Column(name = "name_english", nullable = false)
    private String nameEnglish;

    @Column(name = "name_arabic", nullable = false)
    private String nameArabic;


    @Column(name = "DESCRIPTION_ENGLISH")
    private String descriptionEnglish;

    @Column(name = "DESCRIPTION_ARABIC")
    private String descriptionArabic;

    @Column(name = "IMAGE_URL")
    private String imageUrl;


    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "IS_ACTIVE")
    private boolean isActive = true;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;


    @Column(name = "DELETED_AT")
    private Date deletedAt;
}