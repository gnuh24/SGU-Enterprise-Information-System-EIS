package com.sgu.sale.repositories;

import com.sgu.sale.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category,  Integer>, JpaSpecificationExecutor<Category> {
    boolean existsByCategoryName(String categoryName);
}

