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

import java.util.HashSet;
import java.util.List;

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
        return offerRepository.findAll().stream().map(offer-> modelMapper.map(offer, OfferResponseDTO.class)).toList();
    }

    public List<OfferResponseDTO> findAllOffersByCategory(@Param("categoryId")  Long categoryId) {
        List<Offer> offers =  offerRepository.findAllActiveOffersByCategory(categoryId);
        return offers.stream().map(offer -> modelMapper.map(offer, OfferResponseDTO.class)).toList();
    }

    public OfferResponseDTO findOfferById(@Param("offerId") Long offerId) {
        Offer offer = offerRepository.findById(offerId).get();
        return modelMapper.map(offer, OfferResponseDTO.class);
    }

    public OfferResponseDTO createOffer(OfferRequestDTO offerRequestDTO) throws BadRequestException {
        Offer offer = modelMapper.map(offerRequestDTO, Offer.class);
        List<Category> categories = categoryRepository.findAllById(offerRequestDTO.getCategories());
        if(categories.size() != offerRequestDTO.getCategories().size()) {
            throw new BadRequestException(offerRequestDTO.getCategories().size() - categories.size() + "Categories are invalid");
        }
        if (merchantRepository.findById(offerRequestDTO.getMerchantId()).isEmpty()) {
            throw new BadRequestException(offerRequestDTO.getMerchantId() + "Merchant not found");
        }
        Merchant merchant = merchantRepository.findById(offerRequestDTO.getMerchantId()).get();
        offer.setOfferCategories(new HashSet<>(categories));
        offer.setMerchant(merchant);
        Offer savedOffer = offerRepository.save(offer);
        return modelMapper.map(savedOffer, OfferResponseDTO.class);
    }
}
