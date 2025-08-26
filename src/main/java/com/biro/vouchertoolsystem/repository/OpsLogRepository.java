package com.biro.vouchertoolsystem.repository;

import com.biro.vouchertoolsystem.model.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpsLogRepository extends JpaRepository<OperationLog, Long> {

}
