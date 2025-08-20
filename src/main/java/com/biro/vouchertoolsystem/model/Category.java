package com.biro.vouchertoolsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToMany(mappedBy = "offerCategories")
    private Set<Offer> categoryOffers;

    @Column(name = "name_english", nullable = false)
    private String nameEnglish;

    @Column(name = "name_arabic", nullable = false)
    private String nameArabic;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

}
