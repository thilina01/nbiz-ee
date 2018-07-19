package com.nanosl.nbiz.ee.repository;

import com.nanosl.nbiz.ee.domain.SaleInvoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SaleInvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice, Long> {

}
