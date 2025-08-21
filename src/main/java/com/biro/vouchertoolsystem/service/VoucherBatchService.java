package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Request.BatchRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Request.VoucherRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.BatchVoucherDTO;
import com.biro.vouchertoolsystem.model.Product;
import com.biro.vouchertoolsystem.model.Voucher;
import com.biro.vouchertoolsystem.model.VoucherBatch;
import com.biro.vouchertoolsystem.model.VoucherStatus;
import com.biro.vouchertoolsystem.repository.ProductRepository;
import com.biro.vouchertoolsystem.repository.VoucherBatchRepository;
import com.biro.vouchertoolsystem.repository.VoucherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherBatchService {
    private final VoucherBatchRepository voucherBatchRepository;
    private final ProductRepository productRepository;
    private final VoucherRepository voucherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VoucherBatchService(VoucherBatchRepository voucherBatchRepository, ProductRepository productRepository, VoucherRepository voucherRepository, ModelMapper modelMapper) {
        this.voucherBatchRepository = voucherBatchRepository;
        this.productRepository = productRepository;
        this.voucherRepository = voucherRepository;
        this.modelMapper = modelMapper;
    }

    public List<BatchVoucherDTO> insertNewBatch(BatchRequestDTO  batchRequestDTO) {
        VoucherBatch voucherBatch = new VoucherBatch();

            Product product =  productRepository.findById(batchRequestDTO.getProductId()).orElse(null);
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            voucherBatch.setProduct(product);
            voucherBatch.setTotalCount(batchRequestDTO.getVouchers().size());
            VoucherBatch batch = voucherBatchRepository.save(voucherBatch);
            List<BatchVoucherDTO> createdVouchers = new ArrayList<>();
            for (VoucherRequestDTO dto : batchRequestDTO.getVouchers()) {
                Voucher voucher = new Voucher();
                voucher.setBatch(batch);
                voucher.setCode(dto.getCode());
                voucher.setVoucherStatus(VoucherStatus.AVAILABLE);
                voucher.setExpiryDate(dto.getExpiryDate());
                createdVouchers.add(modelMapper.map(voucherRepository.save(voucher), BatchVoucherDTO.class));
            }
            return createdVouchers;
    }
}
