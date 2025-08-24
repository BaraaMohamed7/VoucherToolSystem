package com.biro.vouchertoolsystem.repository;

import com.biro.vouchertoolsystem.model.VoucherBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherBatchRepository extends JpaRepository<VoucherBatch,Long> {
    @Query("select b.id from VoucherBatch b where b.product.id = :productId")
    VoucherBatch findVoucherBatchByProductId(@Param("productId") Long productId);

    VoucherBatch getVoucherBatchById(Long id);
}
