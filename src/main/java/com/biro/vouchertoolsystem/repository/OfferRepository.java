package com.biro.vouchertoolsystem.repository;

import com.biro.vouchertoolsystem.Dtos.Response.OfferResponseDTO;
import com.biro.vouchertoolsystem.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o JOIN o.offerCategories oc WHERE oc.id = :categoryId and o.isActive = false and o.deletedAt = null")
    List<Offer> findAllActiveOffersByCategory(@Param("categoryId")  Long categoryId);

    List<Offer> findAllByDeletedAtIs(Date deletedAt);

    Offer findByIdAndDeletedAtIs(Long id, Date deletedAt);
}
