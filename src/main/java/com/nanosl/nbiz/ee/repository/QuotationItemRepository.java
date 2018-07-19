package com.nanosl.nbiz.ee.repository;

import com.nanosl.nbiz.ee.domain.QuotationItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuotationItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationItemRepository extends JpaRepository<QuotationItem, Long> {

}
