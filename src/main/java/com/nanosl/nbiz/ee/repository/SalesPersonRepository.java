package com.nanosl.nbiz.ee.repository;

import com.nanosl.nbiz.ee.domain.SalesPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalesPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesPersonRepository extends JpaRepository<SalesPerson, Long> {

}
