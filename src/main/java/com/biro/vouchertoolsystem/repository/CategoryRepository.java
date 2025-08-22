package com.biro.vouchertoolsystem.repository;

import com.biro.vouchertoolsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByDeletedAtIsNullAndIsActiveIsTrueAndIdIn(List<Long> ids);
    List<Category> findAllByDeletedAtIsNullAndIsActiveIsTrue();
    Category findByDeletedAtIsNullAndIsActiveIsTrueAndIdIs(Long id);
}
