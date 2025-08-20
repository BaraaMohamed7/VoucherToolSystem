package com.biro.vouchertoolsystem.Dtos.Request;

import com.biro.vouchertoolsystem.Dtos.Response.CategoryResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OfferRequestDTO {
    private String nameEnglish;
    private String nameArabic;
    private String descriptionEnglish;
    private String descriptionArabic;
    private String imageUrl;
    private Date startDate;
    private Date endDate;
    private Long merchantId;
    private boolean isActive;
    private List<Long> categories;
}
