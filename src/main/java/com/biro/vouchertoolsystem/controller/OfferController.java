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

    @GetMapping
    List<OfferResponseDTO> getAllOffers(){
        return offerService.getAllOffers();
    }

    @GetMapping("/{offerId}")
    OfferResponseDTO getOfferById(@PathVariable Long offerId){
        return offerService.findOfferById(offerId);
    }

    @GetMapping("/category/{categoryId}")
    List<OfferResponseDTO> getOffersOfCategory(@PathVariable Long categoryId){
        return offerService.findAllOffersByCategory(categoryId);
    }


    @PostMapping
    OfferResponseDTO createOffer(@RequestBody OfferRequestDTO offerRequestDTO){
        try {
            return offerService.createOffer(offerRequestDTO);
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PatchMapping("/{offerId}")
    public  OfferResponseDTO updateOffer(@PathVariable Long offerId, @RequestBody OfferRequestDTO offerRequestDTO) throws BadRequestException {
        return offerService.updateOffer(offerId, offerRequestDTO);
    }

    @DeleteMapping("/{offerId}")
    public String deleteOfferById(@PathVariable Long offerId){
        return offerService.deleteOffer(offerId);
    }
}
