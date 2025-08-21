package com.biro.vouchertoolsystem.repository;

import com.biro.vouchertoolsystem.Dtos.Response.OrderVouchersResponseDTO;
import com.biro.vouchertoolsystem.model.Order;
import com.biro.vouchertoolsystem.model.Voucher;
import com.biro.vouchertoolsystem.model.VoucherStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query(value = "select v.id from Voucher v  where v.batch.id = :batchId and v.voucherStatus = :status")
    List<Long> findAvailableVouchersByBatchId(@Param("batchId") Long batchId, @Param("status")VoucherStatus status, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Voucher v SET v.voucherStatus = :newStatus, v.order.id = :orderId WHERE v.id IN :voucherIds")
    void updateVouchersStatusAndOrder(
            @Param("voucherIds") List<Long> voucherIds,
            @Param("orderId") Long orderId,
            @Param("newStatus") VoucherStatus newStatus
    );

    @Query("select v from Voucher v where v.order.id = :orderId")
    List<Voucher> findVouchersByOrderId(@Param("orderId") Long orderId);
}
