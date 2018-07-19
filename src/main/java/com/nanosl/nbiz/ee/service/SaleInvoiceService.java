package com.nanosl.nbiz.ee.service;

import com.nanosl.nbiz.ee.domain.SaleInvoice;
import com.nanosl.nbiz.ee.repository.SaleInvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing SaleInvoice.
 */
@Service
@Transactional
public class SaleInvoiceService {

    private final Logger log = LoggerFactory.getLogger(SaleInvoiceService.class);

    private final SaleInvoiceRepository saleInvoiceRepository;

    public SaleInvoiceService(SaleInvoiceRepository saleInvoiceRepository) {
        this.saleInvoiceRepository = saleInvoiceRepository;
    }

    /**
     * Save a saleInvoice.
     *
     * @param saleInvoice the entity to save
     * @return the persisted entity
     */
    public SaleInvoice save(SaleInvoice saleInvoice) {
        log.debug("Request to save SaleInvoice : {}", saleInvoice);        return saleInvoiceRepository.save(saleInvoice);
    }

    /**
     * Get all the saleInvoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SaleInvoice> findAll(Pageable pageable) {
        log.debug("Request to get all SaleInvoices");
        return saleInvoiceRepository.findAll(pageable);
    }


    /**
     * Get one saleInvoice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SaleInvoice> findOne(Long id) {
        log.debug("Request to get SaleInvoice : {}", id);
        return saleInvoiceRepository.findById(id);
    }

    /**
     * Delete the saleInvoice by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SaleInvoice : {}", id);
        saleInvoiceRepository.deleteById(id);
    }
}
