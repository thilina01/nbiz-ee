package com.nanosl.nbiz.ee.repository;

import com.nanosl.nbiz.ee.domain.SaleInvoicePayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SaleInvoicePayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleInvoicePaymentRepository extends JpaRepository<SaleInvoicePayment, Long> {

}
