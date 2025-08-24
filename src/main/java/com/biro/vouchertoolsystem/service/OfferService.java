package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Request.OfferRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OfferResponseDTO;
import com.biro.vouchertoolsystem.model.Category;
import com.biro.vouchertoolsystem.model.Merchant;
import com.biro.vouchertoolsystem.model.Offer;
import com.biro.vouchertoolsystem.repository.CategoryRepository;
import com.biro.vouchertoolsystem.repository.MerchantRepository;
import com.biro.vouchertoolsystem.repository.OfferRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;
    private final MerchantRepository merchantRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public  OfferService(OfferRepository offerRepository, CategoryRepository categoryRepository, MerchantRepository merchantRepository,  ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
        this.merchantRepository = merchantRepository;
        this.modelMapper = modelMapper;
    }

    public List<OfferResponseDTO> getAllOffers(){
        return offerRepository.findAllByDeletedAtIs(null).stream()
                .map(offer-> modelMapper.map(offer, OfferResponseDTO.class))
                .toList();
    }

    public List<OfferResponseDTO> findAllOffersByCategory(@Param("categoryId")  Long categoryId) {
        List<Offer> offers =  offerRepository.findAllActiveOffersByCategory(categoryId);
        return offers.stream().map(offer -> modelMapper.map(offer, OfferResponseDTO.class)).toList();
    }

    public OfferResponseDTO findOfferById(@Param("offerId") Long offerId) throws BadRequestException {

        Offer offer = offerRepository.findByIdAndDeletedAtIs(offerId, null);
        if (offer == null) {
            throw new BadRequestException("Offer Not Found");
        }
        return modelMapper.map(offer, OfferResponseDTO.class);
    }

    public OfferResponseDTO createOffer(OfferRequestDTO offerRequestDTO) throws BadRequestException {
        Offer offer = modelMapper.map(offerRequestDTO, Offer.class);

        List<Category> categories = categoryRepository.findAllById(offerRequestDTO.getCategories());

        if(categories.size() != offerRequestDTO.getCategories().size()) {
            throw new BadRequestException(offerRequestDTO.getCategories().size() - categories.size() + "Categories are invalid");
        }

        if (merchantRepository.findByIdAndDeletedAtIsNullAndIsActiveIsTrue(offerRequestDTO.getMerchantId()).isEmpty()) {
            throw new BadRequestException(offerRequestDTO.getMerchantId() + "Merchant not found");
        }

        Merchant merchant = merchantRepository.findById(offerRequestDTO.getMerchantId()).get();
        offer.setOfferCategories(new HashSet<>(categories));
        offer.setMerchant(merchant);
        Offer savedOffer = offerRepository.save(offer);
        return modelMapper.map(savedOffer, OfferResponseDTO.class);
    }

    public String deleteOffer(Long offerId) {
        if(offerRepository.findById(offerId).isPresent()) {
            Offer offer = offerRepository.findById(offerId).get();
            offer.setDeletedAt(new Date());
           offerRepository.save(offer);
           return "deleted successfully";
        }
        return "offer not found to be deleted";
    }

    public OfferResponseDTO updateOffer(Long id,OfferRequestDTO offerRequestDTO) throws BadRequestException {
        Optional<Offer> existingOffer = Optional.ofNullable(offerRepository.findByIdAndDeletedAtIs(id, null));
        Offer offer = null;
        if(existingOffer.isPresent()) {
            offer = existingOffer.get();
            if(offerRequestDTO.getNameArabic() != null){
                offer.setNameArabic(offerRequestDTO.getNameArabic());
            }
            if(offerRequestDTO.getDescriptionArabic() != null){
                offer.setDescriptionArabic(offerRequestDTO.getDescriptionArabic());
            }

            if (offerRequestDTO.getNameEnglish() != null){
                offer.setNameEnglish(offerRequestDTO.getNameEnglish());
            }
            if (offerRequestDTO.getDescriptionEnglish() != null){
                offer.setDescriptionEnglish(offerRequestDTO.getDescriptionEnglish());
            }

            if (offerRequestDTO.getImageUrl()  != null){
                offer.setImageUrl(offerRequestDTO.getImageUrl());
            }

            if (offerRequestDTO.getStartDate() != null){
                offer.setStartDate(offerRequestDTO.getStartDate());
            }

            if (offerRequestDTO.getEndDate() != null){
                offer.setEndDate(offerRequestDTO.getEndDate());
            }

            if(offerRequestDTO.getMerchantId() != null){
                if (merchantRepository.findByIdAndDeletedAtIsNullAndIsActiveIsTrue(offerRequestDTO.getMerchantId()).isEmpty()) {
                    throw new BadRequestException(offerRequestDTO.getMerchantId() + "Merchant not found");
                }
                offer.setMerchant(merchantRepository.findById(offerRequestDTO.getMerchantId()).get());
            }

            if(offerRequestDTO.getCategories() != null){
                List<Category> categories = categoryRepository.findAllByDeletedAtIsNullAndIsActiveIsTrueAndIdIn(offerRequestDTO.getCategories());

                if(categories.size() != offerRequestDTO.getCategories().size()) {
                    throw new BadRequestException(offerRequestDTO.getCategories().size() - categories.size() + "Categories are invalid");
                }
                offer.setOfferCategories(new HashSet<>(categories));
            }
            return modelMapper.map(offerRepository.save(offer),  OfferResponseDTO.class);
        } else {
            throw new BadRequestException("Offer not found");
        }
    }
}
