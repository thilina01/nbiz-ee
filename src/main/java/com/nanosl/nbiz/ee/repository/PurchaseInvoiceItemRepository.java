package com.nanosl.nbiz.ee.repository;

import com.nanosl.nbiz.ee.domain.PurchaseInvoiceItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseInvoiceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseInvoiceItemRepository extends JpaRepository<PurchaseInvoiceItem, Long> {

}
