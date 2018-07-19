package com.nanosl.nbiz.ee.repository;

import com.nanosl.nbiz.ee.domain.SaleInvoiceItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SaleInvoiceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleInvoiceItemRepository extends JpaRepository<SaleInvoiceItem, Long> {

}
