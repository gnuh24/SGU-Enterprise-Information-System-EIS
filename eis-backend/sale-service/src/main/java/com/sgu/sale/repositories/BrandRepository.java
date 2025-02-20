package com.sgu.sale.repositories;

import com.sgu.sale.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> , JpaSpecificationExecutor<Brand> {
    /**
     * Finds a Brand by its name.
     *
     * @param brandName the name of the brand to find.
     * @return an Optional containing the Brand if found, otherwise empty.
     */
    Optional<Brand> findByBrandName(String brandName);

    /**
     * Checks if a brand name already exists in the database.
     *
     * @param brandName the name of the brand to check.
     * @return true if a brand with the given name exists, false otherwise.
     */
    boolean existsByBrandName(String brandName);
}
