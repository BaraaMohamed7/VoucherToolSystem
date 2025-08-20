package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.OfferRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OfferResponseDTO;
import com.biro.vouchertoolsystem.service.OfferService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private OfferService offerService;
    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/all")
    List<OfferResponseDTO> getAllOffers(){
        return offerService.getAllOffers();
    }

    @GetMapping("/category")
    List<OfferResponseDTO> getOffersOfCategory(@RequestParam Long categoryId){
        return offerService.findAllOffersByCategory(categoryId);
    }

    @GetMapping("/{offerId}")
    OfferResponseDTO getOfferById(@PathVariable Long offerId){
    return offerService.findOfferById(offerId);
    }

    @PostMapping("/new")
    OfferResponseDTO createOffer(@RequestBody OfferRequestDTO offerRequestDTO){
        try {
            return offerService.createOffer(offerRequestDTO);
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
