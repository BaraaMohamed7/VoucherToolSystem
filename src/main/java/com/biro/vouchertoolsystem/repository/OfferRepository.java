package com.biro.vouchertoolsystem.repository;

import com.biro.vouchertoolsystem.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o JOIN o.offerCategories oc WHERE oc.id = :categoryId")
    List<Offer> findAllActiveOffersByCategory(@Param("categoryId")  Long categoryId);
}
