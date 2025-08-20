package com.biro.vouchertoolsystem.Dtos.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OfferResponseDTO {
    private Long id;
    private String nameEnglish;
    private String nameArabic;
    private String descriptionEnglish;
    private String descriptionArabic;
    private String imageUrl;
    private Date startDate;
    private Date endDate;
    private Long merchantId;
    private String merchantNameEnglish;
    private String merchantNameArabic;
    private List<CategoryResponseDTO> categories;
    private List<OfferProductDTO> products;
}
