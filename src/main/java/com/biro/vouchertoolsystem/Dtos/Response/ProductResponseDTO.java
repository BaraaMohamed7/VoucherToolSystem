package com.biro.vouchertoolsystem.Dtos.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private String nameEnglish;
    private String nameArabic;
    private String descriptionEnglish;
    private String descriptionArabic;
    private String imageUrl;
    private Double priceBefore;
    private Double priceAfter;
    private ProductOfferDTO offer;
}
