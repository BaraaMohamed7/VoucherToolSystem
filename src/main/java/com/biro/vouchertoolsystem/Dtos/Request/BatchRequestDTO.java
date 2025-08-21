package com.biro.vouchertoolsystem.Dtos.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BatchRequestDTO {
    private Long productId;
    List<VoucherRequestDTO> vouchers;
}
